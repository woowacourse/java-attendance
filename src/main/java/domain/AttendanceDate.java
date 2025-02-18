package domain;

import java.time.LocalDateTime;

public class AttendanceDate {
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

    public AttendanceState calculateAttendanceState() {
        return AttendanceState.calculateAttendanceState(this.getDayOfWeek(), this.dateTime);
    }

    private int getDayOfWeek() {
        return this.dateTime.getDayOfWeek().getValue();
    }
}
