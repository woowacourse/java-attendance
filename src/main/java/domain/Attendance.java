package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private Crew crew;
    private LocalDateTime dateTime;
    private String status;

    private Attendance(Crew crew, LocalDateTime dateTime) {
        validateDayOfWeek(dateTime);
        setStatus(dateTime);
        this.crew = crew;
        this.dateTime = dateTime;
    }

    public static Attendance of(Crew crew, LocalDateTime dateTime) {
        return new Attendance(crew, dateTime);
    }

    private void validateDayOfWeek(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
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
        if (time.isBefore(LocalTime.of(hour, 5)) || time.equals(LocalTime.of(hour, 5))) {
            status = "출석";
            return;
        }
        if (time.isAfter(LocalTime.of(hour, 5)) && time.isBefore(LocalTime.of(hour, 30))
            || time.equals(LocalTime.of(hour, 30))) {
            status = "지각";
            return;
        }
        status = "결석";
    }

    public String getStatus() {
        return status;
    }
}
