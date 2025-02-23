package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CustomClock {
    LocalDateTime now();

    LocalDate nowDate();

    boolean isHoliday(LocalDate date);
}
