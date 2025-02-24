package strategy;

import java.time.LocalDate;

public class TestAttendanceNowDateStrategy implements NowDateStrategy {

    private final LocalDate testAttendanceDate;

    public TestAttendanceNowDateStrategy(LocalDate testAttendanceDate) {
        this.testAttendanceDate = testAttendanceDate;
    }

    @Override
    public LocalDate now() {
        return testAttendanceDate;
    }
}
