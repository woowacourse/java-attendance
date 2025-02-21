package attendance.domain;

public enum WarningLevel {
    REMOVE("제적"),
    COUNSELING("면담"),
    WARNING("경고"),
    NONE("해당 없음");
    private final String level;

    WarningLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
