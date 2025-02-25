import java.util.List;


public enum AttendTimeOfWeekDay {

    TEN(10, List.of(2, 3, 4, 5)),
    THIRTEEN(13, List.of(1));

    private final int hour;
    private final List<Integer> days;

    AttendTimeOfWeekDay(int hour, List<Integer> days) {
        this.hour = hour;
        this.days = days;
    }

    public static int getTimeByDayOfWeekDay(int dayOfWeek) {
        for (AttendTimeOfWeekDay attendTimeOfWeekDay : AttendTimeOfWeekDay.values()) {
            if (attendTimeOfWeekDay.days.contains(dayOfWeek))
                return attendTimeOfWeekDay.hour;
        }
        throw new IllegalArgumentException("");
    }

    public int getHour() {
        return hour;
    }

    public List<Integer> getDays() {
        return days;
    }
}
