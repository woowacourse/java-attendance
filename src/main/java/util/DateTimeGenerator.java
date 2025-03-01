package util;

import java.time.LocalDate;

public class DateTimeGenerator {

    private final DateTimeStrategy dateTimeStrategy;

    public DateTimeGenerator(DateTimeStrategy dateTimeStrategy) {
        this.dateTimeStrategy = dateTimeStrategy;
    }

    public LocalDate now() {
        return dateTimeStrategy.now();
    }
}
