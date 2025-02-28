package attendance.model.attendance.log;

import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import java.time.LocalDate;
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

    public boolean containsAttendanceLog(final AttendanceLog attendanceLog) {
        return attendanceLogs.contains(attendanceLog);
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

    public boolean isSameCrew(final Crew crew) {
        return this.crew.equals(crew);
    }

    public void updateAttendanceLog(final AttendanceLog from, final AttendanceLog to) {
        attendanceLogs.update(from, to);
    }

    public List<AttendanceLog> getAllAttendanceLogs(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy) {

        return attendanceLogs.getAllAttendanceLogs(from, to, campusOperationPolicy);
    }
}
