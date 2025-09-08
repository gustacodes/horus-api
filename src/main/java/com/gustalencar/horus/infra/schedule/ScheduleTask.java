package com.gustalencar.horus.infra.schedule;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.repository.AttendanceRepository;
import com.gustalencar.horus.service.EmployeeDailyBalanceService;
import com.gustalencar.horus.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateHorusEmployeeDailyBalance;
import models.responses.UserHorusResponse;
import models.responses.WorkedHoursHorusResponse;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.time.LocalDateTime.now;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleTask {
    private final UserService userService;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeDailyBalanceService dailyBalanceService;

//    @Scheduled(cron = "0 59 23 L * ?")
    @Scheduled(cron = "0 29 15 8 9 ?")
    public void calculateWorkedHours() {
        List<UserHorusResponse> users = userService.findAllByStatus("A");
        List<WorkedHoursHorusResponse> hoursUsers = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

        for (int i = 0; i < users.size(); i++) {
            var user = userService.findById(users.get(i).id());
            for (LocalDate day = firstDay; !day.isAfter(lastDay); day = day.plusDays(1)) {

                List<Attendance> records = attendanceRepository.findByUserAndDate(users.get(i).id(), day);
                records.sort(Comparator.comparing(Attendance::getDateTime));

                LocalDateTime entry = null;
                LocalDateTime lunchOut = null;
                LocalDateTime lunchIn = null;
                LocalDateTime exit = null;

                for (Attendance record : records) {
                    switch (record.getType()) {
                        case ENTRY -> entry = record.getDateTime();
                        case LUNCH_OUT -> lunchOut = record.getDateTime();
                        case LUNCH_IN -> lunchIn = record.getDateTime();
                        case EXIT -> exit = record.getDateTime();
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
                        System.out.println(new WorkedHoursHorusResponse(formatDuration(totalWorked), formatDuration(extraToShow), formatDurationWithSign(saldo), message));
                        hoursUsers.add(new WorkedHoursHorusResponse(formatDuration(totalWorked), formatDuration(extraToShow), formatDurationWithSign(saldo), message));
                    }
                }
            }
        }
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
}
