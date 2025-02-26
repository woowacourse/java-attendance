package strategy;

import java.time.LocalDate;

public class AttendanceCurrentDateGenerateStrategy implements CurrentDateGenerateStrategy {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
