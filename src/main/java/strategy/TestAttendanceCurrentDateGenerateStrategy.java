package strategy;

import java.time.LocalDate;

public class TestAttendanceCurrentDateGenerateStrategy implements CurrentDateGenerateStrategy {

    private LocalDate testAttendanceDate;

    public TestAttendanceCurrentDateGenerateStrategy(LocalDate testAttendanceDate) {
        this.testAttendanceDate = testAttendanceDate;
    }

    public void setTestDate(LocalDate testAttendanceDate) {
        this.testAttendanceDate = testAttendanceDate;

    }

    @Override
    public LocalDate now() {
        return testAttendanceDate;
    }
}
