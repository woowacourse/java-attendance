package domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import util.DateTimeUtil;

public enum Calender {

    MON("월요일", List.of(2, 9, 16, 23, 30)),
    TUE("화요일", List.of(3, 10, 17, 24, 31)),
    WED("수요일", List.of(4, 11, 18)),
    THU("목요일", List.of(5, 12, 19, 26)),
    FRI("금요일", List.of(6, 13, 20, 27)),
    HOLY("공휴일", List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29));

    private final String description;
    private final List<Integer> date;

    Calender(final String description, final List<Integer> date) {
        this.description = description;
        this.date = date;
    }

    public static String findBy(final int dayOfMonth) {
        return Arrays.stream(Calender.values())
                .filter(calender -> calender.date.contains(dayOfMonth))
                .map(calender -> calender.description)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하는 요일이 없습니다."));
    }

    public static void validateHolyDay(final int date) {
        LocalDate localDate = LocalDate.of(2024, 12, date);
        if (DateTimeUtil.isHoliday(localDate)) {
            throw new IllegalArgumentException("공휴일에는 출석을 할 수 없습니다.");
        }
    }
}
