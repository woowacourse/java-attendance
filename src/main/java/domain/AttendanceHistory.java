package domain;

public class AttendanceHistory {
    private final Crew crew;
    private final AttendanceDateTime attendanceDateTime;

    private AttendanceHistory(Crew crew, AttendanceDateTime attendanceDateTime, AttendanceType attendanceType) {
        this.crew = crew;
        this.attendanceDateTime = attendanceDateTime;
    }

    public static AttendanceHistory of(Crew crew, AttendanceDateTime attendanceDateTime) {
        return new AttendanceHistory(crew, attendanceDateTime, attendanceDateTime.getAttendanceType());
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public boolean isSameCrew(AttendanceHistory attendanceHistory) {
        return crew.equals(attendanceHistory.crew);
    }

    public boolean isSameCrew(Crew crew) {
        return this.crew.equals(crew);
    }

    public boolean hasSameDay(AttendanceHistory comparedHistory) {
        return attendanceDateTime.hasSameDay(comparedHistory.attendanceDateTime);
    }

    public int getDay() {
        return attendanceDateTime.getDay();
    }

    public boolean isPastHistory(int day) {
        return attendanceDateTime.getDay() < day;
    }
}