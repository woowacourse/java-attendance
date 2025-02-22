package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TodayDateTimeGenerator implements DateTimeGenerator {
    @Override
    public LocalDate generateDate() {
        return LocalDate.of(2024, 12, 13);
    }
}
