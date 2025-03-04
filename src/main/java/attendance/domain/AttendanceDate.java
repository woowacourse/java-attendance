package attendance.domain;

import java.time.LocalDate;

public class AttendanceDate {
    private static final String INVALID_DATE_FORMAT = "[ERROR] 올바르지 않은 출석 날짜 형식입니다.\n";
    private static final String INVALID_IS_WEEKEND = "[ERROR] 주말에는 출석을 할 수 없습니다.\n";

    private static final String DATE_FORMAT = "^[1-9]|[12][0-9]|3[01]$";

    private static final int TODAY_YEAR_VALUE = LocalDate.now().getYear();
    private static final int TODAY_MONTH_VALUE = LocalDate.now().getMonthValue();

    private static final int END_OF_WEEKDAY_VALUE = 5;

    private final LocalDate attendanceDate;

    public AttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public AttendanceDate(String dateInput) {
        validateAttendanceDate(dateInput);
        this.attendanceDate = LocalDate.of(TODAY_YEAR_VALUE, TODAY_MONTH_VALUE, Integer.parseInt(dateInput));
    }

    private void validateAttendanceDate(final String attendanceDate) {
        if (!attendanceDate.matches(DATE_FORMAT)) {
            throw new IllegalArgumentException(INVALID_DATE_FORMAT);
        }
    }

    public void checkAttendanceDateIsWeekend() {
        if (attendanceDate.getDayOfWeek().getValue() > END_OF_WEEKDAY_VALUE) {
            throw new IllegalArgumentException(INVALID_IS_WEEKEND);
        }
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
}
