package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8,0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        Holiday.check(attendanceDate);
        validateCampusOperatingHours(attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public boolean hasAttend(LocalDate attendanceDate, LocalTime attendanceTime) {
        return this.attendanceDate.isEqual(attendanceDate)
            && this.attendanceTime.equals(attendanceTime);
    }

    private void validateCampusOperatingHours(LocalTime attendanceTime) {
        if (attendanceTime.isBefore(CAMPUS_OPEN_TIME) || attendanceTime.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
