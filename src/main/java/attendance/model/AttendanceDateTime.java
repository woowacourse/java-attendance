package attendance.model;

import java.time.LocalTime;

public class AttendanceDateTime {
    private final AttendanceDate attendanceDate;
    private AttendanceTime attendanceTime;

    public AttendanceDateTime(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public Attendance getAttendanceType() {
        LocalTime startTime = WoowaDurationTime.from(attendanceDate).getStartTime();
        long minuteDelta = attendanceTime.computeMinuteDelta(startTime);
        return Attendance.from(minuteDelta);
    }

    public void modifyAttendanceTime(AttendanceTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public AttendanceDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }
}
