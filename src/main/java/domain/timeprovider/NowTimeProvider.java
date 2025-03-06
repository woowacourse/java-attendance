package domain.timeprovider;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public class NowTimeProvider implements TimeProvider {
    private final Clock clock;

    public NowTimeProvider(Clock clock) {
        this.clock = clock;
    }

    public LocalDate getNowDate() {
        return LocalDate.now(clock);
    }

    public LocalTime getNowTime() {
        return LocalTime.now(clock);
    }
}
