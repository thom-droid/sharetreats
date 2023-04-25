package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatter {

    private static final String FORMAT = "yyyy년 MM월 dd일 HH시 mm분";
    private static final Locale LOCALE = new Locale("ko");

    public static String convertDateToString(LocalDateTime dateToConvert) {
        return DateTimeFormatter.ofPattern(FORMAT, LOCALE).format(dateToConvert);
    }

}
