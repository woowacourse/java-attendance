package attendance.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CustomLocalDateTime {
    private final Clock clock;

    public CustomLocalDateTime() {
        this.clock = Clock.system(ZoneId.of("UTC"));
    }

    public CustomLocalDateTime(Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public LocalDate nowDate() {
        return now().toLocalDate();
    }
}
