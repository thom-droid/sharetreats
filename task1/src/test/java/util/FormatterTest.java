package util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FormatterTest {

    @Test
    void convertDateToString() {

        String expected = "2023년 04월 15일 18시 30분";
        LocalDateTime date = LocalDateTime.of(2023, 4, 15, 18, 30);
        String actual = Formatter.convertDateToString(date);

        assertEquals(expected, actual);
    }

}