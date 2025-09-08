package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.mapper.AttendanceMapper;
import com.gustalencar.horus.repository.AttendanceRepository;
import com.gustalencar.horus.util.Util;
import lombok.RequiredArgsConstructor;
import models.enums.AttendanceStatusEnum;
import models.enums.AttendanceTypeEnum;
import models.exceptions.AmountOfPointsTheDayReached;
import models.requests.CreateAttendanceHorusRequest;
import models.requests.CreateHorusEmployeeDailyBalance;
import models.responses.AttendanceAdjustmentsUserResponse;
import models.responses.UserHorusResponse;
import models.responses.WorkedHoursHorusResponse;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;
    private final UserService userService;
    private final FirmService firmService;
    private final EmployeeDailyBalanceService dailyBalanceService;

    public void registerPoint(CreateAttendanceHorusRequest request) {
        var user = userService.find(request.userId());

        LocalDate today = LocalDate.now();
        List<Attendance> todayRecords = repository.findByUserAndDate(user.getId(), today);

        if (todayRecords.size() == 4) {
            throw new AmountOfPointsTheDayReached("Quantidade de batidas do dia atingida");
        }

        AttendanceTypeEnum nextType = determineNextType(todayRecords);
        Attendance attendance = mapper.fromRequest(request);
        attendance.setUser(user);
        attendance.setDateTime(now().truncatedTo(ChronoUnit.SECONDS));
        attendance.setType(nextType);
        attendance.setStatus(AttendanceStatusEnum.VALID);
        repository.save(attendance);
    }

    private AttendanceTypeEnum determineNextType(List<Attendance> todayRecords) {
        if (todayRecords.isEmpty()) {
            return AttendanceTypeEnum.ENTRY;
        }

        Attendance last = todayRecords.get(todayRecords.size() - 1);

        return switch (last.getType()) {
            case ENTRY -> AttendanceTypeEnum.LUNCH_OUT;
            case LUNCH_OUT -> AttendanceTypeEnum.LUNCH_IN;
            case LUNCH_IN -> AttendanceTypeEnum.EXIT;
            case EXIT -> AttendanceTypeEnum.EXTRA;
            case EXTRA -> AttendanceTypeEnum.EXTRA;
        };
    }

    public List<WorkedHoursHorusResponse> calculateWorkedHours() {
        List<UserHorusResponse> users = userService.findAllByStatus("A");
        List<WorkedHoursHorusResponse> hoursUsers = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

        for (int i = 0; i < users.size(); i++) {
            var user = userService.findById(users.get(i).id());
            for (LocalDate day = firstDay; !day.isAfter(lastDay); day = day.plusDays(1)) {

                List<Attendance> records = repository.findByUserAndDate(users.get(i).id(), day);
                records.sort(Comparator.comparing(Attendance::getDateTime));

                LocalDateTime entry = null;
                LocalDateTime lunchOut = null;
                LocalDateTime lunchIn = null;
                LocalDateTime exit = null;
                List<LocalDateTime> extras = new ArrayList<>();

                for (Attendance record : records) {
                    switch (record.getType()) {
                        case ENTRY -> entry = record.getDateTime();
                        case LUNCH_OUT -> lunchOut = record.getDateTime();
                        case LUNCH_IN -> lunchIn = record.getDateTime();
                        case EXIT -> exit = record.getDateTime();
                        case EXTRA -> extras.add(record.getDateTime());
                    }

                    Duration totalWorked = Duration.ZERO;

                    if (entry != null && lunchOut != null) {
                        totalWorked = totalWorked.plus(Duration.between(entry, lunchOut));
                    }

                    if (lunchIn != null && exit != null) {
                        totalWorked = totalWorked.plus(Duration.between(lunchIn, exit));
                    }

                    if (entry != null && exit != null && lunchOut == null && lunchIn == null) {
                        totalWorked = Duration.between(entry, exit);
                    }

                    Duration expected;
                    if (List.of("ROLE_ATTENDANT", "ROLE_CASHIER", "ROLE_GENERAL_SERVICES").contains(user.profile().name())) {
                        expected = Duration.ofHours(7).plusMinutes(20);
                    } else {
                        expected = Duration.ofHours(8);
                    }

                    Duration saldo = totalWorked.minus(expected);
                    Duration extraToShow = saldo.isNegative() ? Duration.ZERO : saldo;

                    String message = gerarMensagemStatus(entry, lunchOut, lunchIn, exit);
                    if (message.equals("Complete records")) {
                        String balanceType = saldo.isNegative() ? "NEGATIVE" : saldo.isPositive() ? "POSITIVE" : saldo.isZero() ? "NORMAL" : "ZERO";
                        var totalExtra = CreateHorusEmployeeDailyBalance.builder()
                                .user(user)
                                .firm(user.firm())
                                .balanceDate(day)
                                .workedSeconds(totalWorked.getSeconds())
                                .expectedSeconds(expected.getSeconds())
                                .balanceSeconds(saldo.getSeconds())
                                .balanceType(balanceType)
                                .createdAt(now())
                                .updatedAt(now())
                                .build();
                        dailyBalanceService.save(totalExtra);
                        hoursUsers.add(new WorkedHoursHorusResponse(formatDuration(totalWorked), formatDuration(extraToShow), formatDurationWithSign(saldo), message));
                    }
                }
            }
        }
        return hoursUsers;
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    private String formatDurationWithSign(Duration duration) {
        long totalMinutes = duration.toMinutes();
        String sign = totalMinutes < 0 ? "-" : totalMinutes == 0 ? "" : "+";
        totalMinutes = Math.abs(totalMinutes);
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return String.format("%s%02d:%02d", sign, hours, minutes);
    }

    private String gerarMensagemStatus(LocalDateTime entry, LocalDateTime lunchOut, LocalDateTime lunchIn, LocalDateTime exit) {
        if (entry == null) return "No entries recorded";
        if (exit == null) return "Unregistered exit";
        if (lunchOut != null && lunchIn == null) return "Lunch departure registered, but no return";
        return "Complete records";
    }

    public List<AttendanceAdjustmentsUserResponse> adjustmentsUserResponse(final String cpf, final String data) {
        return repository.adjustmentsHoursUser(cpf, data)
                .stream()
                .map(attendance -> {
                    var user = userService.find(attendance.getUser().getId());
                    return AttendanceAdjustmentsUserResponse.builder()
                            .name(user.getName())
                            .data(Util.formatDate(attendance.getDateTime()))
                            .hora(Util.formatHour(attendance.getDateTime()))
                            .attId(attendance.getId())
                            .attObservation(attendance.getObservation())
                            .attType(pointStatusUsers(attendance.getType().name()))
                            .attStatus(validationStatus(attendance.getStatus().name()))
                            .usrProfile(functionUserInFirm(user.getProfile().name()))
                            .usrId(user.getId())
                            .firmId(user.getFirm().getId())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void updateAdjustmentHourUser(String hour, Long attId, String type) {
        repository.updateAdjustmentHourUser(hour, attId, type);
    }

    private String functionUserInFirm(String function) {
        String profile = null;
        switch (function) {
            case "ROLE_GENERAL_SERVICES" -> profile = "SERVIÇOS GERAIS";
            case "ROLE_MANAGER" -> profile = "GERENTE";
            case "ROLE_CASHIER" -> profile = "CAIXA";
            case "ROLE_BAKER" -> profile = "PADEIRO";
            case "ROLE_ATTENDANT" -> profile = "ATENDENTE";
        }
        return profile;
    }

    private String validationStatus(String status) {
        String statusPoint = null;
        switch (status) {
            case "VALID" -> statusPoint = "VALIDO";
            case "INVALID" -> statusPoint = "INVALIDA";
            case "PENDING" -> statusPoint = "PENDENTE";
        }
        return statusPoint;
    }

    private String pointStatusUsers(String statusPoint) {
        switch (statusPoint) {
            case "ENTRY" -> statusPoint = "ENTRADA";
            case "LUNCH_OUT" -> statusPoint = "INTERVALO";
            case "LUNCH_IN" -> statusPoint = "VOLTA";
            case "EXIT" -> statusPoint = "SAÍDA";
        }
        return statusPoint;
    }
}
