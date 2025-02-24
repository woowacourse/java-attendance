package domain;

import java.util.Arrays;

public enum Punishment {

    EXPULSION("제적", 5),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    NONE("없음", 0);

    private final String punishmentName;
    private final int absenceCount;

    Punishment(final String punishmentName, final int absenceCount) {
        this.punishmentName = punishmentName;
        this.absenceCount = absenceCount;
    }

    public static Punishment findByAbsenceCount(final int absenceCount) {
        return Arrays.stream(Punishment.values())
                .filter(punishment -> punishment.absenceCount < absenceCount)
                .findFirst()
                .orElse(NONE);
    }

    public String getPunishmentName() {
        return punishmentName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
