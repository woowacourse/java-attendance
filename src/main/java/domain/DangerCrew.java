package domain;

public class DangerCrew implements Comparable<DangerCrew> {
    private final Crew crew;
    private final int lateCount;
    private final int absenceCount;

    private DangerCrew(Crew crew, int lateCount, int absenceCount) {
        this.crew = crew;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
    }

    public static DangerCrew of(Crew crew, int lateCount, int absenceCount) {
        return new DangerCrew(crew, lateCount, absenceCount);
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    @Override
    public int compareTo(DangerCrew o) {
        int myLate = PenaltyStatus.convertAbsenceToLate(lateCount, absenceCount);
        int otherLate = PenaltyStatus.convertAbsenceToLate(o.lateCount, o.absenceCount);

        if (myLate != otherLate) {
            return otherLate - myLate;
        }
        return this.crew.compareTo(o.crew);
    }

    @Override
    public String toString() {
        return crew.toString();
    }
}
