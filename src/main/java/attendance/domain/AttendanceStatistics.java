package attendance.domain;

import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static attendance.domain.CrewStatus.FIRE;
import static attendance.domain.CrewStatus.INTERVIEW;
import static attendance.domain.CrewStatus.NONE;
import static attendance.domain.CrewStatus.WARNING;

public class AttendanceStatistics implements Comparable<AttendanceStatistics> {
    private static final int FIRE_THRESHOLD = 5;
    private static final int INTERVIEW_THRESHOLD = 2;
    private static final int WARNING_THRESHOLD = 1;
    private static final int LATE_COUNT_AS_ABSENCE = 3;

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
        if (totalAbsence > FIRE_THRESHOLD) {
            return FIRE;
        }
        if (totalAbsence > INTERVIEW_THRESHOLD) {
            return INTERVIEW;
        }
        if (totalAbsence > WARNING_THRESHOLD) {
            return WARNING;
        }
        return NONE;
    }

    private int calculateTotalAbsence() {
        int totalAbsence = absenceCount;
        totalAbsence += (lateCount / LATE_COUNT_AS_ABSENCE);
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
    public int compareTo(AttendanceStatistics given) {
        if (given == null || getClass() != given.getClass()) {
            return 0;
        }
        return given.calculateTotalAbsence() - this.calculateTotalAbsence();
    }
}
