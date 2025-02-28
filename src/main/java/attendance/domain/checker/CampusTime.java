package attendance.domain.checker;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalTime;

public enum CampusTime {
    START_TIME(LocalTime.of(8, 0, 0)),
    END_TIME(LocalTime.of(22, 59, 59));

    private LocalTime time;

    CampusTime(LocalTime time) {
        this.time = time;
    }

    public static void validateCampusTime(LocalTime time) {
        if (!checkInCampusTime(time)) {
            throw new AttendanceException(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
        }
    }

    public static boolean checkInCampusTime(LocalTime time) {
        return !time.isBefore(START_TIME.time) && !time.isAfter(END_TIME.time);
    }

    public LocalTime getTime() {
        return time;
    }
}
