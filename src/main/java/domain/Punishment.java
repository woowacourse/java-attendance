package domain;

import java.time.LocalTime;
import java.util.Arrays;

public enum Punishment {

    EXPULSION("제적", 5),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    NONE("없음", 0);

    // 00:00은 고정된 운영시간에서 불가능한 출석시간이기에 이 시간을 결석 대체 시간으로 사용
    public static final LocalTime ABSENCE_TIME = LocalTime.of(0, 0);
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
