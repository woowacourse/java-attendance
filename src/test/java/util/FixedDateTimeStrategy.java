package util;

import java.time.LocalDateTime;

public class FixedDateTimeStrategy implements DateTimeStrategy {

    private final LocalDateTime fixedDateTime;

    public FixedDateTimeStrategy(LocalDateTime fixedDateTime) {
        this.fixedDateTime = fixedDateTime;
    }

    @Override
    public LocalDateTime now() {
        return fixedDateTime;
    }
}
