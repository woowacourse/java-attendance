package domain;

public enum AttendanceAlertLevel {
    DISMISSED(6, "제적"),
    COUNSEL_REQUIRED(3, "면담"),
    CAUTION(2, "경고"),
    NORMAL(0, "일반"),
    ;

    int absenceLimit;
    String name;

    AttendanceAlertLevel(int absenceLimit, String name) {
        this.absenceLimit = absenceLimit;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
