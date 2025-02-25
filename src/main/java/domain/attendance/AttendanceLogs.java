package domain.attendance;

import domain.crew.CrewStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceLogs {

    private final List<AttendanceLog> attendanceLogs;

    public AttendanceLogs() {
        this.attendanceLogs = new ArrayList<>();
    }

    public void addAttendanceLog(LocalDateTime attendDateTime) {
        this.attendanceLogs.add(new AttendanceLog(attendDateTime));
    }

    public CrewStatus calculateCrewStatus() {
        int lateCount = calculateLateCount();
        int absentCount = calculateAbsentCount();
        return CrewStatus.findStatus(lateCount, absentCount);
    }

    private int calculateLateCount() {
        return (int) attendanceLogs.stream()
                .filter(attendanceLog -> attendanceLog.getAttendanceStatus() == AttendanceStatus.LATE)
                .count();
    }

    private int calculateAbsentCount() {
        return (int) attendanceLogs.stream()
                .filter(attendanceLog -> attendanceLog.getAttendanceStatus() == AttendanceStatus.ABSENT)
                .count();
    }
}
