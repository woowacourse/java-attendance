package domain;

import java.util.List;

public class AttendanceDate {
    private final int month = 12;
    private final int day;

    // TODO: 범위 벗어나면 에러 던지기
    public AttendanceDate(int day) {
        this.day = day;
    }

    public int getDayOfWeek() {
        return ((day + 5) % 7) + 1;
    }

    public boolean isRestDay() {
        List<Integer> restDays = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);
        return restDays.contains(day);
    }
}
