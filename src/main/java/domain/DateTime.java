package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class DateTime implements Comparable<DateTime> {
    private final WorkDate workDate;
    private final WorkTime workTime;

    public DateTime(WorkDate workDate, WorkTime workTime) {
        this.workDate = workDate;
        this.workTime = workTime;
    }

    public static DateTime from(LocalDateTime localDateTime) {
        return new DateTime(
                new WorkDate(localDateTime.toLocalDate().getYear(), localDateTime.toLocalDate().getMonthValue(),
                        localDateTime.toLocalDate().getDayOfMonth()),
                new WorkTime(localDateTime.toLocalTime().getHour(), localDateTime.toLocalTime().getMinute()));
    }

    public WorkDate getDate() {
        return workDate;
    }

    public WorkTime getTime() {
        return workTime;
    }

    @Override
    public int compareTo(DateTime other) {
        int dateDiff = this.workDate.compareTo(other.workDate);
        if (dateDiff != 0) {
            return dateDiff;
        }

        return this.workTime.compareTo(other.workTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateTime dateTime = (DateTime) o;
        return Objects.equals(workDate, dateTime.workDate) && Objects.equals(workTime, dateTime.workTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workDate, workTime);
    }
}
