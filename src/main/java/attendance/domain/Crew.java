package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Crew {
    private final String nickname;
    private final Attendances attendances;

    public Crew(final String nickname, final List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = new Attendances(attendances);
    }

    public boolean isEqualToNickname(final String nickname) {
        return this.nickname.equals(nickname);
    }

    public void existInAttendances(final LocalDate date) {
        attendances.existInAttendances(date);
    }

    public void addAttendance(final Attendance attendance) {
        attendances.addAttendance(attendance);
    }

    public Warning checkWarning() {
        return Warning.check(attendances.calculateTotalAbsenceCount());
    }

    public Attendance updateAttendance(final LocalDateTime dateTime) {
        return attendances.updateAttendance(dateTime);
    }

    public Attendance findAttendanceByDate(final LocalDate updateDate) {
        return attendances.findAttendanceByDate(updateDate);
    }

    public String getNickname() {
        return nickname;
    }

    public Attendances getAttendances() {
        return attendances;
    }
}
