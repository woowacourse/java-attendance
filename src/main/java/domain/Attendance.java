package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.DateUtil;

public class Attendance {

    private LocalDateTime dateTime;
    private AttendanceStatus status;

    private Attendance(LocalDateTime dateTime) {
        validateDayOfWeek(dateTime);
        validateHoliday(dateTime);
        setStatus(dateTime);
        this.dateTime = dateTime;
    }

    public static Attendance of(LocalDateTime dateTime) {
        return new Attendance(dateTime);
    }

    private void validateDayOfWeek(LocalDateTime dateTime) {
        if (DateUtil.isWeekend(dateTime)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateHoliday(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalDate holiday = LocalDate.of(2024, 12, 25);
        if (date.equals(holiday)) {
            throw new IllegalArgumentException();
        }
    }

    private void setStatus(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        if (dateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            determineStatus(time, 13);
            return;
        }

        determineStatus(time, 10);
    }

    private void determineStatus(LocalTime time, int hour) {
        if ((time.isAfter(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(hour, 5)))
            || time.equals(LocalTime.of(hour, 5))) {
            status = AttendanceStatus.CHECKIN;
            return;
        }
        if (time.isAfter(LocalTime.of(hour, 5)) && time.isBefore(LocalTime.of(hour, 30))
            || time.equals(LocalTime.of(hour, 30))) {
            status = AttendanceStatus.LATE;
            return;
        }
        status = AttendanceStatus.ABSENCE;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void update(LocalDateTime updatedDateTime) {
        setStatus(updatedDateTime);
        this.dateTime = updatedDateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
