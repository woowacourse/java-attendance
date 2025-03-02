package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import util.Current;

public class AttendanceRecord {
    private final LocalDate date;
    private final LocalTime attendingTime;

    public AttendanceRecord(LocalDate date, LocalTime attendingTime) {
        this.date = date;
        this.attendingTime = attendingTime;
    }

    public static AttendanceRecord timeOf(String time) {
        return new AttendanceRecord(Current.getToday(), LocalTime.parse(time));
    }

    public static AttendanceRecord of(String date, String time) {
        if (date.length() < 2) {
            date = "0" + date;
        }
        return new AttendanceRecord(LocalDate.parse(Current.getStringOfThisMonth() + "-" + date),
                LocalTime.parse(time));
    }

    public boolean isSameDate(AttendanceRecord attendanceRecord) {
        return date.equals(attendanceRecord.date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceRecord that = (AttendanceRecord) o;
        return Objects.equals(date, that.date) && Objects.equals(attendingTime, that.attendingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, attendingTime);
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" + "date=" + date + ", attendingTime=" + attendingTime + '}';
    }
}
