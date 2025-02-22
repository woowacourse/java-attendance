package attendance.domain;

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
        int attendanceComparison = this.attendanceStatus.compareTo(o.attendanceStatus);
        int pointComparison = this.totalCount.compareTo(o.totalCount);

        if (attendanceComparison != 0) {
            return attendanceComparison;
        }
        if (pointComparison != 0) {
            return -pointComparison;
        }
        return name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PenaltyCrew that = (PenaltyCrew) o;
        return absenceCount == that.absenceCount && lateCount == that.lateCount && Objects.equals(name, that.name)
                && Objects.equals(totalCount, that.totalCount) && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, absenceCount, lateCount, totalCount, attendanceStatus);
    }
}
