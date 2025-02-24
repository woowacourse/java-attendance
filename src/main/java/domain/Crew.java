package domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TreeSet;

public class Crew implements Comparable<Crew> {

    private final Nickname nickname;
    private final Attendances attendances;
    private final AttendanceCounter attendanceCounter;

    public Crew(final Nickname nickname, Attendances attendances, AttendanceCounter attendanceCounter) {
        this.nickname = nickname;
        this.attendances = attendances;
        this.attendanceCounter = attendanceCounter;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public boolean isAttended(final LocalDateTime dateTime) {
        return attendances.isAttended(dateTime);
    }

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance getAttendance(final AttendanceDateTime attendanceDateTime) {
        return attendances.findAttendance(attendanceDateTime.getLocalDateTime());
    }

    public void updateAttendance(final Attendance oldAttendance, final Attendance attendance) {
        attendances.remove(oldAttendance);
        attendances.add(attendance);
    }

    public int calculateAdjustedAbsenceCountWithTardinessCount() {
        return attendanceCounter.calculateAdjustedAbsenceCountWithTardinessCount();
    }

    public AttendanceCounter getAttendanceCounter() {
        return attendanceCounter;
    }

    public TreeSet<Attendance> getAttendances() {
        return attendances.getAttendances();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Crew crew = (Crew) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }

    @Override
    public int compareTo(final Crew o) {
        return this.nickname.getNickname().compareTo(o.getNickname().getNickname());
    }
}
