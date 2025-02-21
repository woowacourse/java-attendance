package attendance.domain;

import java.time.LocalDate;

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

    public String getNickname() {
        return nickname;
    }

    public Attendances getAttendances() {
        return attendances;
    }
}
