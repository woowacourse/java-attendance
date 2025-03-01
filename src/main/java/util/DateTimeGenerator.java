package util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeGenerator {

    private final DateTimeStrategy dateTimeStrategy;

    public DateTimeGenerator(DateTimeStrategy dateTimeStrategy) {
        this.dateTimeStrategy = dateTimeStrategy;
    }

    public LocalDateTime now() {
        return dateTimeStrategy.now();
    }

    public LocalDate getNowLocalDate() {
        return LocalDate.of(
                now().getYear(),
                now().getMonthValue(),
                now().getDayOfMonth()
        );
    }
}
