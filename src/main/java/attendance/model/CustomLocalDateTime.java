package attendance.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CustomLocalDateTime {
    private static Clock clock = Clock.system(ZoneId.of("UTC"));

    public static void setClock(Clock clock) {
        CustomLocalDateTime.clock = clock;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public static LocalDate nowDate() {
        return now().toLocalDate();
    }
}
