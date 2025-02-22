package domain.attendance;

import domain.crew.Crew;
import domain.date.AttendanceDateTime;

public class AttendanceHistory {
    private final Crew crew;
    private final AttendanceDateTime attendanceDateTime;

    private AttendanceHistory(Crew crew, AttendanceDateTime attendanceDateTime) {
        this.crew = crew;
        this.attendanceDateTime = attendanceDateTime;
    }

    public static AttendanceHistory of(Crew crew, AttendanceDateTime attendanceDateTime) {
        return new AttendanceHistory(crew, attendanceDateTime);
    }

    public int getDay() {
        return attendanceDateTime.getDay();
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public boolean isSameCrew(Crew crew) {
        return this.crew.equals(crew);
    }

    public boolean isPastHistory(int day) {
        return attendanceDateTime.getDay() < day;
    }

    public boolean isSameDayAndCrew(AttendanceHistory comparedHistory) {
        return isSameDay(comparedHistory) && isSameCrew(comparedHistory);
    }

    private boolean isSameDay(AttendanceHistory comparedHistory) {
        return attendanceDateTime.hasSameDay(comparedHistory.attendanceDateTime);
    }

    private boolean isSameCrew(AttendanceHistory comparedHistory) {
        return this.crew.equals(comparedHistory.crew);
    }
}
