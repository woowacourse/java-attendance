import java.util.List;

public enum December {
    HOLIDAY(List.of(25)),
    WEEKDAY(List.of(2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 23, 24, 26, 27, 30, 31)),
    WEEKEND(List.of(1, 7, 8, 14, 15, 21, 22, 28, 29));

    private final List<Integer> days;

    December(List<Integer> days) {
        this.days = days;
    }

    public static boolean checkHoliday(int dayOfMonth) {
        return HOLIDAY.days.contains(dayOfMonth);
    }

    public static boolean checkWeekDay(int dayOfMonth){
        return WEEKDAY.days.contains(dayOfMonth);
    }

    public List<Integer> getDays() {
        return days;
    }

}
