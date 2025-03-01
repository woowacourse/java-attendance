package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SystemDateTime {
    LocalDateTime now();

    boolean isWorkingDay(LocalDate date);
}
