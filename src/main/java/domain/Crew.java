package domain;

import exception.CrewException;
import java.time.LocalDate;
import java.util.Objects;

public class Crew implements Comparable<Crew> {

    private final Nickname nickname;
    private final Attendances attendances;
    private final AttendanceCounter attendanceCounter;

    public Crew(final Nickname nickname, final Attendances attendances, final AttendanceCounter attendanceCounter) {
        this.nickname = nickname;
        this.attendances = attendances;
        this.attendanceCounter = attendanceCounter;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public void validateAttended(final AttendanceDateTime attendanceDateTime) {
        LocalDate date = attendanceDateTime.getDate();
        if (isAttended(date)) {
            throw new IllegalArgumentException(CrewException.ALREADY_ATTENDANCE.getMessage());
        }
    }

    private boolean isAttended(final LocalDate date) {
        return attendances.existAttended(date);
    }

    public void add(final Attendance attendance) {
        attendances.addSorted(attendance);
    }


    public Attendances getAttendances() {
        return attendances;
    }

    public AttendanceCounter getAttendanceCounter() {
        return attendanceCounter;
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
