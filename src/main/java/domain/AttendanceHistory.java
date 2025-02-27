package domain;

import java.time.LocalDateTime;

public class AttendanceHistory {
    private final Crew crew;
    private final AttendanceDateTime dateTime;

    private AttendanceHistory(Crew crew, AttendanceDateTime dateTime) {
        this.crew = crew;
        this.dateTime = dateTime;
    }

    public static AttendanceHistory of(Crew crew, AttendanceDateTime dateTime) {
        return new AttendanceHistory(crew, dateTime);
    }

    public static AttendanceHistory of(Crew crew, LocalDateTime dateTime) {
        return new AttendanceHistory(crew, AttendanceDateTime.from(dateTime));
    }

    public AttendanceDateTime getDateTime() {
        return dateTime;
    }

    public boolean hasSameDate(LocalDateTime comparedDateTime) {
        return dateTime.getLocalDateTime().toLocalDate().equals(comparedDateTime.toLocalDate());
    }

    public boolean hasSameDate(AttendanceHistory attendanceHistory) {
        return hasSameDate(attendanceHistory.dateTime.getLocalDateTime());
    }

    public boolean hasSameCrew(Crew comparedCrew) {
       return crew.equals(comparedCrew);
    }

    public boolean hasSameCrew(AttendanceHistory attendanceHistory) {
        return crew.equals(attendanceHistory.crew);
    }

    public AttendanceType getAttendanceType() {
        return dateTime.getAttendanceType();
    }
}
