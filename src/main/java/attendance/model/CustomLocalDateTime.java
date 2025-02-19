package attendance.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class CustomLocalDateTime {
    private static final Clock clock = Clock.fixed(LocalDateTime.of(2024, 12, 16, 12, 0).toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC"));

    public static LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

}
