package attendance.util;

import java.time.LocalDate;

public class CurrentDateGenerator implements DateGenerator {

    @Override
    public LocalDate generate() {
        return LocalDate.of(2025, 2, 24);
    }
}
