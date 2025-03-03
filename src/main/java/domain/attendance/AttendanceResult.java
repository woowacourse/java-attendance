package domain.attendance;

import domain.crew.CrewStatus;
import java.util.Map;

public class AttendanceResult {

    private final Map<AttendanceStatus, Integer> attendanceStatus;
    private final CrewStatus crewStatus;

    public AttendanceResult(Map<AttendanceStatus, Integer> attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
        this.crewStatus = CrewStatus.findStatus(attendanceStatus.get(AttendanceStatus.LATE),
                attendanceStatus.get(AttendanceStatus.ABSENT));
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatus() {
        return attendanceStatus;
    }

    public CrewStatus getCrewStatus() {
        return crewStatus;
    }
}
