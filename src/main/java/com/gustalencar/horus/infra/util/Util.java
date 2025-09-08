package com.gustalencar.horus.infra.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Util {

    public static String formatDate(LocalDateTime dateTime) {
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return formattedDate;
    }

    public static String formatHour(LocalDateTime dateTime) {
        String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return formattedTime;
    }
}
