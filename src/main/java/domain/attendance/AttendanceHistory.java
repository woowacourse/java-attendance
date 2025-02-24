package domain.attendance;

import domain.crew.Crew;
import domain.date.AttendanceDateTime;

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

    public boolean aboutSameCrew(Crew crew) {
        return this.crew.equals(crew);
    }

    public boolean hasSameDay(int day) {
        return attendanceDateTime.hasSameDay(day);
    }

    public int getDay() {
        return attendanceDateTime.getDay();
    }

    public boolean isPastHistory(int day) {
        return attendanceDateTime.getDay() < day;
    }
}
