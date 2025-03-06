package domain;

import java.time.LocalDate;

public class FixedDateProvider implements DateProvider {
    private final LocalDate fixedDate;

    private FixedDateProvider(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
    }

    public static FixedDateProvider of(LocalDate fixedDate) {
        return new FixedDateProvider(fixedDate);
    }

    @Override
    public LocalDate now() {
        return fixedDate;
    }
}
