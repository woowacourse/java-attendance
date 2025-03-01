package attendance.domain;

public class RiskCrew {
    private final String name;
    private final int absenceCount;
    private final int lateCount;
    private final PenaltyType penaltyType;

    public RiskCrew(String name, int absenceCount, int lateCount, PenaltyType penaltyType) {
        this.name = name;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.penaltyType = penaltyType;
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

    public PenaltyType getPenaltyType() {
        return penaltyType;
    }
    
}

