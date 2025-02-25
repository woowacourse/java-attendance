import java.util.List;

public enum December {
    HOLYDAY(List.of(25));

    private final List<Integer> days;

    December(List<Integer> days) {
        this.days = days;
    }

    public static boolean checkHolyDay(int dayOfMonth) {
        return HOLYDAY.days.contains(dayOfMonth);
    }

    public List<Integer> getDays() {
        return days;
    }

}
