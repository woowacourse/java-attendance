package attendance.domain;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

public class PenaltyCrew implements Comparable<PenaltyCrew> {

    private final String name;
    private final PenaltyCount penaltyCount;
    private final AttendancePenalty penalty;

    public PenaltyCrew(String name, Map<AttendanceStatus, Integer> statusCounts) {
        this.name = name;
        this.penaltyCount = new PenaltyCount(statusCounts);
        this.penalty = penaltyCount.findAttendancePenalty();
    }

    public AttendancePenalty getPenalty() {
        return penalty;
    }

    public String getName() {
        return name;
    }

    public int getWeightedLateAndAbsencePoint() {
        return penaltyCount.getWeightedLateAndAbsencePoint();
    }

    public int getLateCount() { return penaltyCount.getLateCount();}

    public int getAbsenceCount() { return penaltyCount.getAbsenceCount();}

    @Override
    public int compareTo(PenaltyCrew o) {
        return Comparator.comparing(PenaltyCrew::getPenalty)
            .thenComparing(PenaltyCrew::getWeightedLateAndAbsencePoint, Comparator.reverseOrder())
            .thenComparing(PenaltyCrew::getName)
            .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PenaltyCrew that = (PenaltyCrew) o;
        return Objects.equals(name, that.name) && Objects.equals(penaltyCount, that.penaltyCount) && penalty == that.penalty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, penaltyCount, penalty);
    }
}
