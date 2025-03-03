package domain;

import java.util.Collections;
import java.util.List;

public class AttendanceAnalyze {
    private final List<AttendanceHistory> histories;

    public AttendanceAnalyze(final List<AttendanceHistory> histories) {
        this.histories = histories;
    }

    public boolean isExpulsionTarget() {
        AttendanceStatus attendanceStatus = getAttendanceStatus();
        return !attendanceStatus.equals(AttendanceStatus.NORMAL);
    }

    public AttendanceStatus getAttendanceStatus() {
        int lateCount = getLateCount();
        int absenceCount = getAbsenceCount();
        return AttendanceStatus.findAttendanceStatus(lateCount, absenceCount);
    }

    public int getLateCount() {
        return (int) histories.stream()
                .filter(history -> history.getAttendanceResult().equals(AttendanceResult.LATE))
                .count();
    }

    public int getAbsenceCount() {
        return (int) histories.stream()
                .filter(history -> history.getAttendanceResult().equals(AttendanceResult.ABSENCE))
                .count();
    }

    public int getAttendanceCount() {
        return (int) histories.stream()
                .filter(history -> history.getAttendanceResult().equals(AttendanceResult.ATTENDANCE))
                .count();
    }

    public int calculatePenaltyPoints() {
        return getAbsenceCount() * 3 + getLateCount();
    }

    public List<AttendanceHistory> getAttendanceHistories() {
        return Collections.unmodifiableList(histories);
    }
}
