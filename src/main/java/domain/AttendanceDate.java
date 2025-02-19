package domain;

import java.util.List;

public class AttendanceDate {
    private final int month = 12;
    private final int day;

    public AttendanceDate(int day) {
        validate(day);
        this.day = day;
    }

    private void validate(int day) {
        if (day > 31 || day <= 0) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfWeek() {
        return ((day + 5) % 7) + 1;
    }

    // TODO: 정적 선언이 맞을까 고민
    public static boolean isRestDay(int day) {
        List<Integer> restDays = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);
        return restDays.contains(day);
    }
}
