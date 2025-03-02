package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class Attendance {

    private final static LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private final static LocalTime CLOSE_TIME = LocalTime.of(23, 0);
    private final static LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    private final LocalDate date;
    private final LocalTime time;

    public Attendance(LocalDate date, LocalTime time) {
        validateDate(date);
        validateTime(time);
        this.date = date;
        this.time = time;
    }

    public AttendanceStatus determineStatus() {
        return AttendanceStatus.from(date, time);
    }

    public boolean isBeforeDate(LocalDate date) {
        return this.date.isBefore(date);
    }

    public boolean hasSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public LocalTime getTime() {
        return time;
    }

    private void validateTime(LocalTime time) {
        if (time.isBefore(OPEN_TIME) || time.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private void validateDate(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        if (dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일") || date.equals(CHRISTMAS)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %02d일 %s은(는) 등교일이 아닙니다.", date.getMonthValue(), date.getDayOfMonth(),
                            dayOfWeek));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
