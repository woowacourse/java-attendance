package model;

public enum StudentPunishment {
    WARNING(2,"경고"),
    INTERVIEW(3,"면담"),
    DISMISSAL(5,"제적");

    private final int absenceLimit;
    private final String punishmentLabel;

    StudentPunishment(int absenceLimit, String punishmentLabel) {
        this.absenceLimit = absenceLimit;
        this.punishmentLabel = punishmentLabel;
    }

    public int getStandard() {
        return absenceLimit;
    }

    public String getPunishmentLabel() {
        return punishmentLabel;
    }

}
