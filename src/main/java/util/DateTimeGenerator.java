package util;

import java.time.LocalDateTime;

public class DateTimeGenerator {

    private final DateTimeStrategy dateTimeStrategy;

    public DateTimeGenerator(DateTimeStrategy dateTimeStrategy) {
        this.dateTimeStrategy = dateTimeStrategy;
    }

    public LocalDateTime now() {
        return dateTimeStrategy.now();
    }
}
