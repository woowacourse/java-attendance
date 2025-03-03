package domain;

import java.util.Arrays;

public enum CrewStatus {
    NORMAL(2, "정상", 4),
    WARNING(3, "경고", 3),
    COUNSEL(6, "면담", 2),
    EXPELLED(0, "제적", 1);

    final int absentLimit;
    final String korean;
    final int priority;

    CrewStatus(int absentLimit, String korean, int priority) {
        this.absentLimit = absentLimit;
        this.korean = korean;
        this.priority = priority;
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

    public int getPriority() {
        return priority;
    }
}
