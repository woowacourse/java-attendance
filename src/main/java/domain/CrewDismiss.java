package domain;

import java.util.Map;
import java.util.Objects;

public class CrewDismiss {

    private static final int LATE_WEIGHT = 3;
    private static final int WARNING_STANDARD = 2;
    private static final int NEED_MEETING_STANDARD = 3;
    private static final int DISMISS_STANDARD = 5;

    private final int absence;
    private final int late;
    private final int attendance;
    private final DismissStatus dismissStatus;

    public CrewDismiss(Map<AttendanceStatus, Integer> crewAttendanceStatusCount) {
        absence = crewAttendanceStatusCount.getOrDefault(AttendanceStatus.ABSENCE, 0);
        late = crewAttendanceStatusCount.getOrDefault(AttendanceStatus.LATE, 0);
        attendance = crewAttendanceStatusCount.getOrDefault(AttendanceStatus.ATTENDANCE, 0);
        dismissStatus = calculateDismissStatus();
    }

    private DismissStatus calculateDismissStatus() {
        int absenceCount = late / LATE_WEIGHT + absence;
        return calculateDismissStatus(absenceCount);
    }

    private DismissStatus calculateDismissStatus(int absenceCount) {
        if (absenceCount > DISMISS_STANDARD) {
            return DismissStatus.DISMISS;
        }
        if (absenceCount >= NEED_MEETING_STANDARD) {
            return DismissStatus.NEED_MEETING;
        }
        if (absenceCount >= WARNING_STANDARD) {
            return DismissStatus.WARNING;
        }
        return DismissStatus.ELSE;
    }

    public AttendanceCount attendanceCount() {
        return new AttendanceCount(attendance, late, absence);
    }

    public DismissStatus dismissStatus() {
        return dismissStatus;
    }

    public String dismissStatusMessage() {
        return dismissStatus.status;
    }

    public int absence() {
        return absence;
    }

    public int late() {
        return late;
    }

    public int attendance() {
        return attendance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrewDismiss that = (CrewDismiss) o;
        return absence == that.absence && late == that.late && attendance == that.attendance
                && dismissStatus == that.dismissStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(absence, late, attendance, dismissStatus);
    }
}
