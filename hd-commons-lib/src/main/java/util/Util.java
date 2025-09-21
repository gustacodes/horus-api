package util;

public class Util {

    public static String onlyNumbers(String input) {
        if (input == null) return null;
        return input.replaceAll("\\D", "");
    }
}
