package domain;

import error.CustomIllegalArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void validateAttended(final LocalDateTime dateTime) {
        if (isAttended(dateTime)) {
            throw new CustomIllegalArgumentException("이미 출석했습니다. 다음에는 수정기능을 이용해주세요.");
        }
    }

    private boolean isAttended(final LocalDateTime dateTime) {
        return attendances.isAttended(dateTime);
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
