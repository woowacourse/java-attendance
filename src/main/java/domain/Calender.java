package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public enum Calender {

    MON("월요일", DayOfWeek.MONDAY),
    TUE("화요일", DayOfWeek.TUESDAY),
    WEN("수요일", DayOfWeek.WEDNESDAY),
    THU("목요일", DayOfWeek.THURSDAY),
    PRI("금요일", DayOfWeek.FRIDAY),
    SAT("토요일", DayOfWeek.SATURDAY),
    SUN("일요일", DayOfWeek.SUNDAY);

    private final String description;
    private final DayOfWeek dayOfWeek;

    Calender(final String description, final DayOfWeek dayOfWeek) {
        this.description = description;
        this.dayOfWeek = dayOfWeek;
    }

    public static Calender findBy(final LocalDate localDate) {
        return findBy(localDate.getDayOfWeek());
    }

    public static Calender findBy(final LocalDateTime localDateTime) {
        return findBy(localDateTime.getDayOfWeek());
    }

    private static Calender findBy(final DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(calender -> calender.dayOfWeek.equals(dayOfWeek))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 요일 입니다."));
    }

    public static void validateHolyDay(final LocalDate localDate) {
        if (isWeekend(localDate)) {
            Calender dayOfWeek = findBy(localDate);

            throw new IllegalArgumentException(String.format("%02d월 %02d일 %s은 등교일이 아닙니다.",
                    localDate.getMonth().getValue(),
                    localDate.getDayOfMonth(),
                    dayOfWeek.description));
        }
    }

    public static boolean isWeekend(final LocalDate localDate) {
        return localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || HolyDay.isHolyDay(localDate);
    }

    public static boolean isMonday(final LocalDateTime time) {
        return MON.dayOfWeek.equals(time.getDayOfWeek());
    }

    public String getDescription() {
        return description;
    }
}
