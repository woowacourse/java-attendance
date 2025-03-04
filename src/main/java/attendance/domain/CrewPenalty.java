package attendance.domain;

public enum CrewPenalty {
    WARNING("경고", 2),
    MEETING("면담", 3),
    EXPEL("제적", 5),
    NONE("비", 1);

    private final String penalty;
    private final int penaltyCount;

    CrewPenalty(String penaltyInput, int penaltyCount) {
        this.penalty = penaltyInput;
        this.penaltyCount = penaltyCount;
    }

    public static CrewPenalty of(final int penaltyCount) {
        if (penaltyCount > EXPEL.getPenaltyCount()) {
            return EXPEL;
        }
        if (penaltyCount >= MEETING.getPenaltyCount()) {
            return MEETING;
        }
        if (penaltyCount == WARNING.getPenaltyCount()) {
            return WARNING;
        }
        return NONE;
    }

    public int getPenaltyCount() {
        return penaltyCount;
    }

    @Override
    public String toString() {
        return penalty;
    }
}
