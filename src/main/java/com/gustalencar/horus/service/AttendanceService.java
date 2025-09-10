package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.infra.util.Util;
import com.gustalencar.horus.mapper.AttendanceMapper;
import com.gustalencar.horus.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import models.enums.AttendanceStatusEnum;
import models.enums.AttendanceTypeEnum;
import models.exceptions.AmountOfPointsTheDayReached;
import models.exceptions.NotUpdateHoursUserException;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateAttendanceHorusRequest;
import models.responses.AttendanceAdjustmentsUserResponse;
import models.responses.AttendanceHorusResponse;
import models.responses.UserHorusResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;
    private final UserService userService;

    public AttendanceHorusResponse findById(final Long attId) {
        return mapper.fromEntity(find(attId));
    }

    public Attendance find(Long attId) {
        return repository.findById(attId).orElseThrow(() -> new ResourceNotFoundException(
                "User not found. Id: " + attId + ", Type: " + UserHorusResponse.class.getSimpleName()
        ));
    }

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
                            .usrProfile(functionUserInCompany(user.getCompanyOccupationId().getName()))
                            .usrId(user.getId())
                            .cmpId(user.getCmpId().getId())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void updateAdjustmentHourUser(String hour, Long attId, String type) {
        var attendance = findById(attId);
        YearMonth attendanceMonth = YearMonth.from(attendance.dateTime());
        YearMonth currentMonth = YearMonth.now();
        if (!attendanceMonth.equals(currentMonth)) {
            throw new NotUpdateHoursUserException("Somente é possível alterar as horas antes do fechamento da folha");
        }
        repository.updateAdjustmentHourUser(hour, attId, type);
    }

    private String functionUserInCompany(String function) {
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
