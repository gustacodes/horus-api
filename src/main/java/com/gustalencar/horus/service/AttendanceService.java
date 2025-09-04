package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.mapper.AttendanceMapper;
import com.gustalencar.horus.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import models.enums.AttendanceStatusEnum;
import models.enums.AttendanceTypeEnum;
import models.requests.CreateAttendanceHorusRequest;
import models.responses.WorkedHoursHorusResponse;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        AttendanceTypeEnum nextType = determineNextType(todayRecords);

        attendance.setFirm(firm);
        attendance.setUser(user);
        attendance.setDateTime(LocalDateTime.now());
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

        if (records.isEmpty()) {
            return new WorkedHoursHorusResponse("00:00", "00:00", "00:00", "Nenhuma batida encontrada");
        }

        records.sort(Comparator.comparing(Attendance::getDateTime));

        LocalDateTime entry = null;
        LocalDateTime lunchOut = null;
        LocalDateTime lunchIn = null;
        LocalDateTime exit = null;
        List<LocalDateTime> extras = new ArrayList<>();

        // Identificar batidas
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

        // Período antes do almoço
        if (entry != null && lunchOut != null) {
            totalWorked = totalWorked.plus(Duration.between(entry, lunchOut));
        }

        // Período depois do almoço
        if (lunchIn != null && exit != null) {
            totalWorked = totalWorked.plus(Duration.between(lunchIn, exit));
        }

        // Caso não tenha almoço registrado
        if (entry != null && exit != null && lunchOut == null && lunchIn == null) {
            totalWorked = Duration.between(entry, exit);
        }

        // Se houver batidas extras
        Duration extraWorked = Duration.ZERO;
        if (!extras.isEmpty() && exit != null) {
            for (LocalDateTime extra : extras) {
                extraWorked = extraWorked.plus(Duration.between(exit, extra));
            }
        }

        // Considerando jornada padrão de 8h
        Duration expected = Duration.ofHours(7).plusMinutes(20);
        Duration saldo = totalWorked.plus(extraWorked).minus(expected);

        return new WorkedHoursHorusResponse(
                formatDuration(totalWorked),
                formatDuration(extraWorked),
                formatDuration(saldo),
                gerarMensagemStatus(entry, lunchOut, lunchIn, exit)
        );
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }

    private String gerarMensagemStatus(LocalDateTime entry, LocalDateTime lunchOut, LocalDateTime lunchIn, LocalDateTime exit) {
        if (entry == null) return "Nenhuma entrada registrada";
        if (exit == null) return "Saída não registrada";
        if (lunchOut != null && lunchIn == null) return "Saída para almoço registrada, mas não há retorno";
        return "Batidas completas";
    }
}
