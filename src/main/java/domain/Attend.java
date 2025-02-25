package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Attend {

    private final LocalDate date;
    private final LocalTime time;

    private Attend(LocalDate date, LocalTime time) {
        validateDateIsNotNull(date);
        this.date = date;
        this.time = time;
    }

    private void validateDateIsNotNull(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date는 null이 될 수 없음");
        }
    }

    public static Attend fromDay(final int day) {
        OperationTime.validateDay(day);
        return new Attend(LocalDate.of(Current.TODAY.getYear(), Current.TODAY.getMonth(), day), null);
    }

    public static Attend fromTime(LocalTime time) {
        return new Attend(Current.TODAY.getDate(), time);
    }

    public static Attend of(final LocalDate day, final LocalTime time) {
        return new Attend(day, time);
    }

    public String formatDate(DateTimeFormatter dateTimeFormatter) {
        return this.date.format(dateTimeFormatter);
    }

    public String formatTime(DateTimeFormatter dateTimeFormatter) {
        return this.time.format(dateTimeFormatter);
    }

    public boolean isDayOff() {
        return this.date.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue()
                || Holiday.isHoliday(this.date);
    }

    public boolean isTimeOff(LocalTime startTime, LocalTime endTime) {
        return hasTime() && (this.time.isBefore(startTime) || this.time.isAfter(endTime));
    }

    public boolean isBefore(final LocalTime targetTime) {
        return hasTime() && this.time.isBefore(targetTime);
    }

    public boolean isEqual(final LocalTime targetTime) {
        return hasTime() && this.time.equals(targetTime);
    }

    public boolean isAfter(final LocalTime targetTime) {
        return hasTime() && this.time.isAfter(targetTime);
    }

    public boolean hasTime() {
        return this.time != null;
    }

    public boolean isDayEqual(Attend attend) {
        return getDay() == attend.getDay();
    }

    public boolean isDayEqual(final int day) {
        return getDay() == day;
    }

    public int getDay() {
        return this.date.getDayOfMonth();
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
