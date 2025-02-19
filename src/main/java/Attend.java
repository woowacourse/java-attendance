import java.time.LocalDateTime;

public class Attend {
    public LocalDateTime time;

    public static Attend of(final String day, final String time) {
        return new Attend(DateUtil.parseDatetime(day, time));
    }

    public static Attend of(String time) {
        return new Attend(DateUtil.parseDatetime(time));
    }

    public Attend(LocalDateTime localDateTime) {
        this.time = localDateTime;
    }

    public boolean isDayEqual(Attend attend) {
        return getDay() == attend.getDay();
    }

    public boolean isDayEqual(final int day) {
        return DateUtil.isDayEqual(day, time);
    }

    public int getDay() {
        return time.getDayOfMonth();
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }
}
