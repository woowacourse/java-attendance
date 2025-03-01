package util;

import java.time.LocalDate;

public class FixedDateTimeStrategy implements DateTimeStrategy {

    private final LocalDate fixedDate;

    public FixedDateTimeStrategy(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
    }

    @Override
    public LocalDate now() {
        return fixedDate;
    }
}
