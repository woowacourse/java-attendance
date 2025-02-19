package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceDate implements Comparable<AttendanceDate> {
    public static final int DEFAULT_START_TIME = 2024;
    public static final int DEFAULT_START_MONTH = 12;
    public static final int DEFAULT_START_DAY = 2;
    public static final LocalDate DEFAULT_START_DATE = LocalDate.of(DEFAULT_START_TIME, DEFAULT_START_MONTH,
            DEFAULT_START_DAY);

    private LocalDateTime dateTime;

    public AttendanceDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;

        int dayOfWeek = getDayOfWeek();
        if (dayOfWeek > 5) {
            throw new IllegalArgumentException("");
        }
        if (Holiday.has(dateTime)) {
            throw new IllegalArgumentException("");
        }
    }

    public void editDateTime(LocalDateTime editDateTime) {
        this.dateTime = editDateTime;
    }

    public LocalDateTime checkAttendanceTime() {
        return this.dateTime;
    }

    public AttendanceState calculateAttendanceState() {
        return AttendanceState.calculateAttendanceState(this.getDayOfWeek(), this.dateTime);
    }

    public boolean equals(LocalDate compareDate) {
        return (this.dateTime.getYear() == compareDate.getYear()
                && this.dateTime.getMonthValue() == compareDate.getMonthValue()
                && this.dateTime.getMonthValue() == compareDate.getMonthValue());
    }

    private int getDayOfWeek() {
        return this.dateTime.getDayOfWeek().getValue();
    }

    @Override
    public int compareTo(AttendanceDate compareAttendanceDate) {
        if (this.dateTime.isBefore(compareAttendanceDate.dateTime)) {
            return 1;
        }
        if (this.dateTime.isEqual(compareAttendanceDate.dateTime)) {
            return 0;
        }
        return -1;
    }
}
