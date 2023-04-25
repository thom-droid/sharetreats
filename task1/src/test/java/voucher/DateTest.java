package voucher;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DateTest {

    @Test
    public void randomDateTest() {

        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 4, 30, 23, 59);
        Random random = new Random();

        LocalDate startLocalDate = start.toLocalDate();
        LocalDate endLocalDate = end.toLocalDate();
        Period period = Period.between(startLocalDate, endLocalDate);

        int days = period.getDays();
        int months = period.getMonths();

        LocalDateTime randomDateTime = startLocalDate.plusDays(random.nextInt(days)).plusMonths(months).atTime(23, 59);

        assertTrue(randomDateTime.isAfter(start));
        assertTrue(randomDateTime.isBefore(end));

    }

}
