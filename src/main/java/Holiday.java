import java.util.List;

public class Holiday {
    private static final List<Integer> saturdays = List.of(7, 14, 21, 28);
    private static final List<Integer> sundays = List.of(1, 8, 15, 22, 29);
    private static final List<Integer> holidays = List.of(25);

    public static void validateIsWorkingDay(int date) {
        if (saturdays.contains(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] 12월 %02d일 토요일은 등교일이 아닙니다.", date));
        }
        if (sundays.contains(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] 12월 %02d일 일요일은 등교일이 아닙니다.", date));
        }
        if (holidays.contains(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] 12월 %02d일 공휴일은 등교일이 아닙니다.", date));
        }
    }
}