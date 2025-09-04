package com.gustalencar.horus.util;

import org.springframework.stereotype.Component;

@Component
public class Utils {

    public static String onlyNumbersForCnpj(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\\D", "");
    }
}
