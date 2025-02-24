package strategy;

import java.time.LocalDate;

public class AttendanceNowDateStrategy implements NowDateStrategy {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
