package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import util.DateUtil;

public class Attend {

    public LocalDate date;
    public LocalTime time;

    public static Attend fromDay(final int day) {
        return new Attend(LocalDate.of(2024, 12, day), null);
    }

    public static Attend of(final String day, final String time) {
        return new Attend(DateUtil.parseDate(day), DateUtil.parsetime(time));
    }

    public static Attend of(String time) {
        return new Attend(LocalDate.of(2024, 12, Current.TODAY.getDay()), DateUtil.parsetime(time));
    }

    public Attend(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public boolean isDayEqual(Attend attend) {
        return getDay() == attend.getDay();
    }

    public boolean isDayEqual(final int day) {
        return DateUtil.isDayEqual(day, date);
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Attend attend = (Attend) object;
        return Objects.equals(date, attend.date) && Objects.equals(time, attend.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
