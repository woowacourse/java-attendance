package strategy;

import java.time.LocalDate;

public class TestAttendanceCurrentDateGenerateStrategy implements CurrentDateGenerateStrategy {

    private final LocalDate testAttendanceDate;

    public TestAttendanceCurrentDateGenerateStrategy(LocalDate testAttendanceDate) {
        this.testAttendanceDate = testAttendanceDate;
    }

    @Override
    public LocalDate now() {
        return testAttendanceDate;
    }
}
