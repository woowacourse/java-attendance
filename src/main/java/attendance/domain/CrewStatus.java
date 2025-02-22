package attendance.domain;

public enum CrewStatus {
    FIRE("제적", 6, 3),
    INTERVIEW("면담", 3, 2),
    WARNING("경고", 2, 1),
    CLEAR("통과", 0, 0);

    private final String name;
    private final int threshold;
    private final int order;

    CrewStatus(String name, int threshold, int order) {
        this.name = name;
        this.threshold = threshold;
        this.order = order;
    }

    public static CrewStatus calculateByAbsenceCount(int absenceCount) {
        if (absenceCount > FIRE.threshold) {
            return FIRE;
        }
        if (absenceCount >= INTERVIEW.threshold) {
            return INTERVIEW;
        }
        if (absenceCount >= WARNING.threshold) {
            return WARNING;
        }
        return CLEAR;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }
}
