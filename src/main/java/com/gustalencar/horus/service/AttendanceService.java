package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.entity.User;
import com.gustalencar.horus.mapper.AttendanceMapper;
import com.gustalencar.horus.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import models.enums.AttendanceStatusEnum;
import models.enums.AttendanceTypeEnum;
import models.exceptions.AmountOfPointsTheDayReached;
import models.requests.CreateAttendanceHorusRequest;
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

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;
    private final UserService userService;
    private final FirmService firmService;

    public void registerPoint(CreateAttendanceHorusRequest request) {
        Attendance attendance = mapper.fromRequest(request);
        var user = userService.find(request.userId());
        var firm = firmService.find(request.firmId());

        LocalDate today = LocalDate.now();
        List<Attendance> todayRecords = repository.findByUserAndDate(user.getId(), today);

        if (todayRecords.size() == 4) {
            throw new AmountOfPointsTheDayReached("Quantidade de batidas do dia atingida");
        }

        AttendanceTypeEnum nextType = determineNextType(todayRecords);

        attendance.setFirm(firm);
        attendance.setUser(user);
        attendance.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
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

    public WorkedHoursHorusResponse calculateWorkedHours(Long userId, LocalDate date) {
        List<Attendance> records = repository.findByUserAndDate(userId, date);
        var user = userService.find(userId);

        if (records.isEmpty()) {
            return new WorkedHoursHorusResponse("00:00", "00:00", "00:00", "Nenhuma batida encontrada");
        }

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
        if (List.of("ROLE_ATTENDANT", "ROLE_CASHIER", "ROLE_GENERAL_SERVICES").contains(user.getProfile())) {
            expected = Duration.ofHours(7).plusMinutes(20);
        } else {
            expected = Duration.ofHours(8);
        }

        Duration saldo = totalWorked.minus(expected);
        Duration extraToShow = saldo.isNegative() ? Duration.ZERO : saldo;

        return new WorkedHoursHorusResponse(
                formatDuration(totalWorked),
                formatDuration(extraToShow),
                formatDurationWithSign(saldo),
                gerarMensagemStatus(entry, lunchOut, lunchIn, exit)
        );
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    private String formatDurationWithSign(Duration duration) {
        long totalMinutes = duration.toMinutes();
        String sign = totalMinutes < 0 ? "-" : "+";
        totalMinutes = Math.abs(totalMinutes);
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return String.format("%s%02d:%02d", sign, hours, minutes);
    }


    private String gerarMensagemStatus(LocalDateTime entry, LocalDateTime lunchOut, LocalDateTime lunchIn, LocalDateTime exit) {
        if (entry == null) return "Nenhuma entrada registrada";
        if (exit == null) return "Saída não registrada";
        if (lunchOut != null && lunchIn == null) return "Saída para almoço registrada, mas não há retorno";
        return "Batidas completas";
    }

}
