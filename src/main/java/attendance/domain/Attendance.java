package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        Holiday.check(attendanceDate);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public boolean hasAttend(LocalDate attendanceDate, LocalTime attendanceTime) {
        return this.attendanceDate.isEqual(attendanceDate)
            && this.attendanceTime.equals(attendanceTime);
    }
}
