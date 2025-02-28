package attendance.model.crew;

import attendance.model.attendance.status.AttendanceStatus;
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

    public static CrewStatus fromAttendanceStatuses(final List<AttendanceStatus> attendanceStatuses) {
        final int absenceCount = calculatePolicyAppliedAbsenceCount(attendanceStatuses);

        if (absenceCount > 5) {
            return EXPULSION;
        }
        if (absenceCount > 2) {
            return CONSULTATION;
        }
        if (absenceCount > 1) {
            return WARNING;
        }
        return NORMAL;
    }

    private static int calculatePolicyAppliedAbsenceCount(final List<AttendanceStatus> attendanceStatuses) {
        return Math.toIntExact(
                attendanceStatuses.stream()
                        .filter(AttendanceStatus.ABSENCE::equals)
                        .count()
        ) + calculateAbsenceCountFromLate(attendanceStatuses);
    }

    private static int calculateAbsenceCountFromLate(final List<AttendanceStatus> attendanceStatuses) {
        return Math.toIntExact(
                attendanceStatuses.stream()
                        .filter(AttendanceStatus.LATE::equals)
                        .count()
        ) / 3;
    }

    public String getName() {
        return name;
    }
}
