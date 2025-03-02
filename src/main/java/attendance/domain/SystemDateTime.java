package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SystemDateTime {
    LocalDateTime now();

    LocalDate nowDate();

    List<LocalDate> extractWorkingDays();

    boolean isWorkingDay(LocalDate date);
}
