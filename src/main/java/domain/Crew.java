package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    /**
     * 출석 횟수 / 지각 횟수 / 결석 횟수 / 보정된 결석 횟수(지각 3회는 결석 1회로 간주), 처벌(제적, 면담, 경고)
     *
     * @return CrewSummary
     */
    public CrewSummary getCrewSummary() {
        final String displayName = this.nickname.getNickname();
        final int absence = attendanceCounter.getAbsence();
        final int tardiness = attendanceCounter.getTardiness();
        final int attendanceCount = attendanceCounter.getAttendanceCount();
        final int adjustedAbsenceCount = attendanceCounter.calculateAdjustedAbsenceCountWithTardinessCount();
        final Punishment punishment = Punishment.findByAbsenceCount(adjustedAbsenceCount);

        return new CrewSummary(displayName, absence, tardiness, attendanceCount, adjustedAbsenceCount, punishment);
    }

    public List<AttendanceSummary> getAttendancesSummary() {
        return attendances.getAttendanceSummary();
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
