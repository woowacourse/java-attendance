package domain;

import java.time.LocalDateTime;

public class AttendanceHistory {
    private final Crew crew;
    private final AttendanceDateTime attendanceDateTime;

    private AttendanceHistory(Crew crew, AttendanceDateTime attendanceDateTime) {
        this.crew = crew;
        this.attendanceDateTime = attendanceDateTime;
    }

    public static AttendanceHistory of(Crew crew, AttendanceDateTime dateTime) {
        return new AttendanceHistory(crew, dateTime);
    }

    public static AttendanceHistory of(Crew crew, LocalDateTime dateTime) {
        return new AttendanceHistory(crew, AttendanceDateTime.from(dateTime));
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public boolean isRecorded() {
        return attendanceDateTime.isRecorded();
    }

    public boolean hasSameDate(LocalDateTime comparedDateTime) {
        return attendanceDateTime.getLocalDateTime().toLocalDate().equals(comparedDateTime.toLocalDate());
    }

    public boolean hasSameDate(AttendanceHistory attendanceHistory) {
        return hasSameDate(attendanceHistory.attendanceDateTime.getLocalDateTime());
    }

    public boolean hasSameCrew(Crew comparedCrew) {
       return crew.equals(comparedCrew);
    }

    public boolean hasSameCrew(AttendanceHistory attendanceHistory) {
        return crew.equals(attendanceHistory.crew);
    }

    public AttendanceType getAttendanceType() {
        return attendanceDateTime.getAttendanceType();
    }
}
