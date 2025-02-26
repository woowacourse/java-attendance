import java.util.List;

public enum December {
    HOLIDAY(List.of(25));

    private final List<Integer> days;

    December(List<Integer> days) {
        this.days = days;
    }

    public static boolean checkHolyDay(int dayOfMonth) {
        return HOLIDAY.days.contains(dayOfMonth);
    }

    public List<Integer> getDays() {
        return days;
    }

}
