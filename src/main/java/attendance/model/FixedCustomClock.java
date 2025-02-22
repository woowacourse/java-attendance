package attendance.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

public class FixedCustomClock implements CustomClock {
    private final Clock clock;
    private final Set<LocalDate> holidayDate;

    public FixedCustomClock(Set<LocalDate> holidayDate) {
        this.clock = Clock.fixed(
                LocalDate.of(2024, 12, 16).atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant(),
                ZoneId.of("Asia/Seoul")
        );
        this.holidayDate = holidayDate;
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public LocalDate nowDate() {
        return now().toLocalDate();
    }

    public boolean isHoliday(LocalDate date) {
        return holidayDate.contains(date);
    }
}