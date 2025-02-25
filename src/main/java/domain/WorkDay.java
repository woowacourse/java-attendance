package domain;

import java.time.DayOfWeek;

public enum WorkDay {
    MONDAY("월", 13, 18),
    TUESDAY("화", 10, 18),
    WEDNESDAY("수", 10, 18),
    THURSDAY("목", 10, 18),
    FRIDAY("금", 10, 18),
    SATURDAY("토", null, null),
    SUNDAY("일", null, null);

    private final String dayOfWeekKorean;
    private final Integer startHour;
    private final Integer endHour;

    WorkDay(String dayOfWeekKorean, Integer startHour, Integer endHour) {
        this.dayOfWeekKorean = dayOfWeekKorean;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static WorkDay from(DayOfWeek dayOfWeek) {
        return WorkDay.valueOf(dayOfWeek.name());
    }

    public String getDayOfWeekKorean() {
        return dayOfWeekKorean;
    }

    public Integer getStartHour() {
        return startHour;
    }
}
