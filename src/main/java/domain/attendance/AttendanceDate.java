package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import presentation.DateTimeUtil;

public class AttendanceDate implements Comparable<AttendanceDate> {
    private static final int DEFAULT_START_TIME = 2024;
    private static final int DEFAULT_START_MONTH = 12;
    private static final int DEFAULT_START_DAY = 2;

    public static final LocalDate DEFAULT_START_DATE = LocalDate.of(DEFAULT_START_TIME, DEFAULT_START_MONTH,
            DEFAULT_START_DAY);

    private LocalDateTime dateTime;

    public AttendanceDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (dayOfWeek.compareTo(DayOfWeek.SATURDAY) > 0) {
            throw new IllegalArgumentException(
                    DateTimeUtil.convertLocalDateToString(dateTime.toLocalDate()) + "은 등교일이 아닙니다.");
        }
        if (Holiday.has(dateTime.toLocalDate())) {
            throw new IllegalArgumentException(
                    DateTimeUtil.convertLocalDateToString(dateTime.toLocalDate()) + "은 등교일이 아닙니다.");
        }
    }

    public void editDateTime(LocalDateTime editDateTime) {
        this.dateTime = editDateTime;
    }

    public LocalDateTime checkAttendanceTime() {
        return this.dateTime;
    }

    public LocalDate convertLocalDate() {
        return this.dateTime.toLocalDate();
    }

    public AttendanceState calculateAttendanceState() {
        return AttendanceState.calculateAttendanceState(this.dateTime.getDayOfWeek(), this.dateTime);
    }

    public boolean equals(LocalDate compareDate) {
        return (this.dateTime.getYear() == compareDate.getYear()
                && this.dateTime.getMonthValue() == compareDate.getMonthValue()
                && this.dateTime.getDayOfMonth() == compareDate.getDayOfMonth());
    }

    @Override
    public int compareTo(AttendanceDate compareAttendanceDate) {
        return this.dateTime.compareTo(compareAttendanceDate.dateTime);
    }
}
