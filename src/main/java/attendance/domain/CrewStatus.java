package attendance.domain;

public enum CrewStatus {
    NONE(""),
    WARNING("경고"),
    INTERVIEW("면담"),
    FIRE("제적");

    private final String name;

    CrewStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
