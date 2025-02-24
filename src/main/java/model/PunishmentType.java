package model;

public enum PunishmentType {

    WARNING(3),
    MEETING(2),
    EXPULSION(1),
    NONE(4);

    private final int priority;

    PunishmentType(int priority) {
        this.priority = priority;
    }

    public static PunishmentType calculateType(int convertedAbsenceCount) {
        if (convertedAbsenceCount > 5) {
            return EXPULSION;
        }
        if (convertedAbsenceCount >= 3) {
            return MEETING;
        }
        if (convertedAbsenceCount >= 2) {
            return WARNING;
        }
        return NONE;
    }

    public int comparePriority(PunishmentType o) {
        return priority - o.priority;
    }
}
