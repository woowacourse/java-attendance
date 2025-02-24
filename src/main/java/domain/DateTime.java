package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class DateTime implements Comparable<DateTime> {
    private final Date date;
    private final Time time;

    public DateTime(Date date, Time time) {
        this.date = date;
        this.time = time;
    }

    public static DateTime of(LocalDateTime localDateTime) {
        return new DateTime(new Date(localDateTime.toLocalDate()),
                new Time(localDateTime.toLocalTime().getHour(), localDateTime.toLocalTime().getMinute()));
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateTime dateTime = (DateTime) o;
        
        return Objects.equals(date, dateTime.date) && Objects.equals(time, dateTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    @Override
    public int compareTo(DateTime o) {
        return date.getDayValue() - (o.date.getDayValue());
    }
}
