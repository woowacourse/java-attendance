package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import util.DateTimeUtil;

public class AttendanceDate implements Comparable<AttendanceDate> {
    public static final int SATURDAY = 6;

    private LocalDateTime dateTime;

    public AttendanceDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;

        int dayOfWeek = getDayOfWeek();
        if (dayOfWeek >= SATURDAY) {
            throw new IllegalArgumentException(DateTimeUtil.convertLocalDateToString(dateTime.toLocalDate()) + "은 등교일이 아닙니다.");
        }
        if (Holiday.has(dateTime)) {
            throw new IllegalArgumentException(DateTimeUtil.convertLocalDateToString(dateTime.toLocalDate()) + "은 등교일이 아닙니다.");
        }
    }

    public AttendanceState calculateAttendanceState() {
        return AttendanceState.calculateAttendanceState(this.getDayOfWeek(), this.dateTime);
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

    public boolean isEqualsLocalDate(LocalDate compareDate) {
        return (this.dateTime.getYear() == compareDate.getYear()
                && this.dateTime.getMonthValue() == compareDate.getMonthValue()
                && this.dateTime.getDayOfMonth() == compareDate.getDayOfMonth());
    }

    @Override
    public int compareTo(AttendanceDate compareAttendanceDate) {
        if (this.dateTime.isBefore(compareAttendanceDate.dateTime)) {
            return -1;
        }
        if (this.dateTime.isEqual(compareAttendanceDate.dateTime)) {
            return 0;
        }
        return 1;
    }

    private int getDayOfWeek() {
        return this.dateTime.getDayOfWeek().getValue();
    }
}
