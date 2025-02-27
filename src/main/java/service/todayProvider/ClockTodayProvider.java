package service.todayProvider;

import java.time.Clock;
import java.time.LocalDate;

public class ClockTodayProvider implements TodayProvider {
    
    private final Clock clock;
    
    public ClockTodayProvider(final Clock clock) {
        this.clock = clock;
    }
    
    @Override
    public LocalDate today() {
        return LocalDate.now(clock);
    }
}
