package domain;

import java.util.Arrays;

public enum CrewStatus {
    NORMAL(2, "정상"),
    WARNING(3, "경고"),
    COUNSEL(6, "면담"),
    EXPELLED(0, "제적");

    final int absentLimit;
    final String korean;

    CrewStatus(int absentLimit, String korean) {
        this.absentLimit = absentLimit;
        this.korean = korean;
    }

    public static CrewStatus calculateCrewStatus(int absentTotal) {
        return Arrays.stream(CrewStatus.values())
                .filter(status -> status.absentLimit > absentTotal)
                .findFirst()
                .orElse(EXPELLED);
    }

    public String getKorean() {
        return korean;
    }
}
