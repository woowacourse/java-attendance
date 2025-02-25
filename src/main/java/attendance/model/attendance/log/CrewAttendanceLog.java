package attendance.model.attendance.log;

import attendance.model.crew.Crew;

public class CrewAttendanceLog {

    private final Crew crew;
    private final AttendanceLogs attendanceLogs;

    public CrewAttendanceLog(Crew crew, AttendanceLogs attendanceLogs) {
        this.crew = crew;
        this.attendanceLogs = attendanceLogs;
    }
}
