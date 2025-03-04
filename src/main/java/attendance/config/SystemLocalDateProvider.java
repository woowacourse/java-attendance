package attendance.config;

import attendance.domain.LocalDateProvider;
import java.time.LocalDate;

public class SystemLocalDateProvider implements LocalDateProvider {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
