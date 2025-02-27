package attendance;

public enum AbsenceRule {

    EXPULSION(5),
    COUNSELING(3),
    WARNING(2),
    NONE(0);

    private final int criteria;

    AbsenceRule(final int criteria) {
        this.criteria = criteria;
    }

    public static AbsenceRule of(final long totalExpulsionCount) {
        for (AbsenceRule value : AbsenceRule.values()) {
            if(totalExpulsionCount >= value.criteria) {
                return value;
            }
        }
        return NONE;
    }
}
