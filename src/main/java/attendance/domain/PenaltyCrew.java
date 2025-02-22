package attendance.domain;

import java.util.Comparator;
import java.util.Objects;

public class PenaltyCrew implements Comparable<PenaltyCrew> {

    public static final int LATE_TO_ABSENCE_RATIO = 3;

    private final String name;
    private final int absenceCount;
    private final int lateCount;
    private final Integer totalCount;
    private final AttendancePenalty attendanceStatus;

    public PenaltyCrew(String name, int absenceCount, int lateCount) {
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.name = name;
        this.totalCount = calculateTotalCount(absenceCount, lateCount);
        this.attendanceStatus = AttendancePenalty.find(absenceCount, lateCount);
    }

    private static int calculateTotalCount(int absenceCount, int lateCount) {
        return absenceCount * LATE_TO_ABSENCE_RATIO + lateCount;
    }

    @Override
    public int compareTo(PenaltyCrew o) {
        return Comparator.comparing(PenaltyCrew::getAttendanceStatus)
            .thenComparing(PenaltyCrew::getTotalCount, Comparator.reverseOrder())
            .thenComparing(PenaltyCrew::getName)
            .compare(this, o);
    }

    public String getName() {
        return name;
    }

    public int getLateCount() {
        return lateCount;
    }

    public Integer getTotalCount() { return totalCount; }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public AttendancePenalty getAttendanceStatus() { return attendanceStatus; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PenaltyCrew that = (PenaltyCrew) o;
        return absenceCount == that.absenceCount && lateCount == that.lateCount && Objects.equals(name, that.name) && Objects.equals(totalCount, that.totalCount) && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, absenceCount, lateCount, totalCount, attendanceStatus);
    }
}
