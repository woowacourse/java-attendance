package attendance.domain;

import attendance.common.Constants;

import java.util.Comparator;
import java.util.Objects;

public class PenaltyCrew implements Comparable<PenaltyCrew> {

    private final String name;
    private final int absenceCount;
    private final int lateCount;
    private final Integer weightedLateAbsencePoint;
    private final AttendancePenalty attendanceStatus;

    public PenaltyCrew(String name, int absenceCount, int lateCount) {
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.name = name;
        this.weightedLateAbsencePoint = calculateTotalCount(absenceCount, lateCount);
        this.attendanceStatus = AttendancePenalty.find(absenceCount, lateCount);
    }

    private static int calculateTotalCount(int absenceCount, int lateCount) {
        return absenceCount * Constants.LATE_TO_ABSENCE_RATIO + lateCount;
    }

    @Override
    public int compareTo(PenaltyCrew o) {
        return Comparator.comparing(PenaltyCrew::getAttendanceStatus)
            .thenComparing(PenaltyCrew::getWeightedLateAbsencePoint, Comparator.reverseOrder())
            .thenComparing(PenaltyCrew::getName)
            .compare(this, o);
    }

    public String getName() {
        return name;
    }

    public int getLateCount() {
        return lateCount;
    }

    public Integer getWeightedLateAbsencePoint() { return weightedLateAbsencePoint; }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public AttendancePenalty getAttendanceStatus() { return attendanceStatus; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PenaltyCrew that = (PenaltyCrew) o;
        return absenceCount == that.absenceCount
            && lateCount == that.lateCount
            && Objects.equals(name, that.name)
            && Objects.equals(weightedLateAbsencePoint, that.weightedLateAbsencePoint)
            && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, absenceCount, lateCount, weightedLateAbsencePoint, attendanceStatus);
    }
}
