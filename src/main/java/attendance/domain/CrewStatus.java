package attendance.domain;

public enum CrewStatus {
    FIRE("제적", 3),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    CLEAR("통과", 0);

    private final String name;
    private final int order;

    CrewStatus(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }
}
