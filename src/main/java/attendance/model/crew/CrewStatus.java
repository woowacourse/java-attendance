package attendance.model.crew;

import attendance.model.attendance.AttendanceStatus;
import java.util.List;

public enum CrewStatus {

    WARNING("경고"),
    CONSULTATION("면담"),
    EXPULSION("제적"),
    NORMAL("정상");

    private final String name;

    CrewStatus(String name) {
        this.name = name;
    }

    public static CrewStatus froAttendanceStatuses(final List<AttendanceStatus> attendanceStatuses) {
        return NORMAL;
    }
}
