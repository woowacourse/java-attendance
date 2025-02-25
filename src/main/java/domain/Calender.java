package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public enum Calender {

    MON("월요일", DayOfWeek.MONDAY),
    TUE("화요일", DayOfWeek.TUESDAY),
    WED("수요일", DayOfWeek.WEDNESDAY),
    THU("목요일", DayOfWeek.THURSDAY),
    FRI("금요일", DayOfWeek.FRIDAY),
    SAT("토요일", DayOfWeek.SATURDAY),
    SUN("일요일", DayOfWeek.SUNDAY);

    private static final List<Integer> HOLY_DAYS = List.of(25);

    private final String description;
    private final DayOfWeek dayOfWeek;

    Calender(final String description, final DayOfWeek dayOfWeek) {
        this.description = description;
        this.dayOfWeek = dayOfWeek;
    }

    public static Calender findBy(final DayOfWeek dayOfWeek) {
        return Arrays.stream(Calender.values())
                .filter(calender -> calender.dayOfWeek.equals(dayOfWeek))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하는 요일이 없습니다."));
    }

    public static boolean isHolyDay(final LocalDate dayOfWeek) {
        return Calender.SAT.dayOfWeek.equals(dayOfWeek.getDayOfWeek()) ||
                Calender.SUN.dayOfWeek.equals(dayOfWeek.getDayOfWeek()) ||
                HOLY_DAYS.contains(dayOfWeek.getDayOfMonth());
    }

    public static void validateHolyDay(final LocalDate dayOfWeek) {
        if (Calender.SAT.dayOfWeek.equals(dayOfWeek.getDayOfWeek()) ||
                Calender.SUN.dayOfWeek.equals(dayOfWeek.getDayOfWeek()) ||
                HOLY_DAYS.contains(dayOfWeek.getDayOfMonth())) {
            throw new IllegalArgumentException("공휴일에는 출석을 할 수 없습니다.");
        }
    }

    public String getDescription() {
        return description;
    }
}
