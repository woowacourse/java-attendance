package attendance.domain;

import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static attendance.domain.CrewStatus.FIRE;
import static attendance.domain.CrewStatus.INTERVIEW;
import static attendance.domain.CrewStatus.NONE;
import static attendance.domain.CrewStatus.WARNING;

public class AttendanceStatistics implements Comparable {
    private final int attendanceCount;
    private final int lateCount;
    private final int absenceCount;

    public AttendanceStatistics(final int attendanceCount, final int lateCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
    }

    public CrewStatus calculateCrewStatus() {
        int totalAbsence = calculateTotalAbsence();
        if (totalAbsence > 5) {
            return FIRE;
        }
        if (totalAbsence > 2) {
            return INTERVIEW;
        }
        if (totalAbsence > 1) {
            return WARNING;
        }
        return NONE;
    }

    private int calculateTotalAbsence() {
        int totalAbsence = absenceCount;
        totalAbsence += (lateCount / 3);
        return totalAbsence;
    }

    public int getStatusCount(final AttendanceStatus status) {
        if (status.equals(ATTENDANCE)) {
            return attendanceCount;
        }
        if (status.equals(LATE)) {
            return lateCount;
        }
        return absenceCount;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return 0;
        }
        AttendanceStatistics that = (AttendanceStatistics) o;
        return -(that.calculateTotalAbsence() - this.calculateTotalAbsence());
    }
}
