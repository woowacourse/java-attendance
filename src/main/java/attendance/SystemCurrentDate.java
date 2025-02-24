package attendance;

import java.time.LocalDate;

public class SystemCurrentDate implements CurrentDate {
    @Override
    public LocalDate now() {
        return LocalDate.of(2024, 12, 31);
    }
}
