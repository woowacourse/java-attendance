package attendance.config;

import attendance.domain.LocalDateProvider;
import java.time.LocalDate;

public class FixedLocalDateProvider implements LocalDateProvider {
    private final LocalDate fixedDate;

    public FixedLocalDateProvider(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
    }

    @Override
    public LocalDate now() {
        return fixedDate;
    }
}
