package attendance.domain;

public enum WarningLevel {
    REMOVE("제적"), COUNSELING("면담"), WARNING("경고");

    private String level;

    WarningLevel(final String level){
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

}
