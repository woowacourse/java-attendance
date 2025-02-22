package domain.date;

import java.util.List;

public class AttendanceDate {
    private final int month = 12;
    private final int day;

    public AttendanceDate(int day) {
        validate(day);
        this.day = day;
    }

    public static void validate(int day) {
        if (day > 31 || day <= 0) {
            throw new IllegalArgumentException("올바른 일 형식이 아닙니다. 1부터 31사이의 값을 입력해주세요.");
        }
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public static int getDayOfWeek(int day) {
        return ((day + 5) % 7) + 1;
    }

    public int getDayOfWeek() {
        return ((day + 5) % 7) + 1;
    }

    public static boolean isRestDay(int day) {
        List<Integer> restDays = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);
        return restDays.contains(day);
    }
}
