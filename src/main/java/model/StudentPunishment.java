package model;

public enum StudentPunishment {
    WARNING(2,"경고"),
    INTERVIEW(3,"면담"),
    DISMISSAL(5,"제적");

    private final int standard;
    private final String punishmentLabel;

    StudentPunishment(int standard, String punishmentLabel) {
        this.standard = standard;
        this.punishmentLabel = punishmentLabel;
    }

    public int getStandard() {
        return standard;
    }

}
