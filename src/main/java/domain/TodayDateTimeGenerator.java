package domain;

import java.time.LocalDate;

public class TodayDateTimeGenerator implements DateTimeGenerator {
    @Override
    public LocalDate generateDate() {
        return LocalDate.of(2024, 12, 13);
    }
}
