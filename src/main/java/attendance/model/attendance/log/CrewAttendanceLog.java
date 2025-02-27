package attendance.model.attendance.log;

import attendance.model.crew.Crew;
import java.util.List;

public class CrewAttendanceLog {

    private final Crew crew;
    private final AttendanceLogs attendanceLogs;

    public CrewAttendanceLog(Crew crew, AttendanceLogs attendanceLogs) {
        this.crew = crew;
        this.attendanceLogs = attendanceLogs;
    }

    public void addAttendanceLog(final AttendanceLog attendanceLog) {
        attendanceLogs.add(attendanceLog);
    }

    public List<AttendanceLog> getAttendanceLogs() {
        return attendanceLogs.getValues();
    }

    public int getAbsenceCount() {
        return attendanceLogs.getAbsenceCount();
    }

    public int getLateCount() {
        return attendanceLogs.getLateCount();
    }

    public int getPolicyAppliedAbsenceCount() {
        return attendanceLogs.getPolicyAppliedAbsenceCount();
    }

    public int getPolicyAppliedLateCount() {
        return attendanceLogs.getPolicyAppliedLateCount();
    }

    public String getCrewNickname() {
        return crew.getNickName();
    }
}
