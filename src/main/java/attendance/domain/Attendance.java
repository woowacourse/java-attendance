package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final String crewName;
    private final Time attendanceTime;

    public Attendance(String crewName, Time attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public boolean isSameLocalDate(String crewName, LocalDate localDate) {
        return this.crewName.equals(crewName) && attendanceTime.isSameLocalDate(localDate);
    }

    public Attendance modifyAttendanceTime(LocalTime modifyTime) {
        attendanceTime.modify(modifyTime);
        return this;
    }
}
