package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Crew implements Comparable<Crew> {
    public static final int LATE_TO_ABSENCE_UNIT = 3;

    private final String nickname;
    private final Attendances attendances;

    public Crew(String nickname, Attendances attendances) {
        this.nickname = nickname;
        this.attendances = attendances;
    }

    public boolean isEqualToNickname(final String nickname) {
        return this.nickname.equals(nickname);
    }

    public Warning checkWarning() {
        return Warning.check(calculateTotalAbsenceCount());
    }

    private long calculateTotalAbsenceCount() {
        return attendances.countAbsence() + (attendances.countLate() / LATE_TO_ABSENCE_UNIT);
    }

    public Attendance findAttendanceByDate(final LocalDate updateDate) {
        return attendances.findAttendanceByDate(updateDate);
    }

    @Override
    public int compareTo(Crew o) {
        long targetAbsenceCount = o.calculateTotalAbsenceCount();
        long targetLateCount = o.attendances.countLate() % LATE_TO_ABSENCE_UNIT;

        long absenceCount = this.calculateTotalAbsenceCount();
        long lateCount = this.attendances.countLate() % LATE_TO_ABSENCE_UNIT;

        if (targetAbsenceCount > absenceCount) {
            return 1;
        }
        if (targetAbsenceCount < absenceCount) {
            return -1;
        }

        if (targetLateCount > lateCount) {
            return 1;
        }
        if (targetLateCount < lateCount) {
            return -1;
        }
        return nickname.compareTo(o.getNickname());
    }

    public void existInAttendances(LocalDate today) {
        attendances.existInAttendances(today);
    }

    public void addAttendance(Attendance attendance) {
        attendances.addAttendance(attendance);
    }

    public Attendance updateAttendance(LocalDateTime updateTime) {
        return attendances.updateAttendance(updateTime);
    }

    public String getNickname() {
        return nickname;
    }

    public long countAttend() {
        return attendances.countAttend();
    }

    public long countLate() {
        return attendances.countLate();
    }

    public long countAbsence() {
        return attendances.countAbsence();
    }

    public List<Attendance> getAttendances() {
        return attendances.getAttendances();
    }
}
