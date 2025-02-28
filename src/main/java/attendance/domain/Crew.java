package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Crew implements Comparable<Crew> {
    private static final int LATE_TO_ABSENCE_UNIT = 3;

    private final String nickname;
    private final Attendances attendances;

    public Crew(final String nickname) {
        this.nickname = nickname;
        this.attendances = new Attendances();
    }

    public Attendance addAttendance(final LocalDateTime attendanceDateTime) {
        Attendance attendance = new Attendance(attendanceDateTime);
        attendances.add(attendance);
        return attendance;
    }

    public void fillAbsentAttendances(LocalDate today) {
        attendances.fillAbsentAttendances(today);
    }

    public boolean isEqualCrew(final String nickname) {
        return this.nickname.equals(nickname);
    }

    public Attendance updateAttendance(final LocalDate updateDate, final LocalTime updateTime) {
        Attendance beforeAttendance = findAttendanceByDate(updateDate);
        attendances.remove(beforeAttendance);
        Attendance updatedAttendance = beforeAttendance.updateAttendanceTime(updateTime);
        attendances.add(updatedAttendance);
        return updatedAttendance;
    }

    public Attendance findAttendanceByDate(final LocalDate date) {
        return attendances.getAttendances().stream()
                .filter(attendance -> attendance.isEqualDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_UPDATE_ATTENDANCE));
    }

    public long countAttendanceStatus(final AttendanceStatus status) {
        return attendances.countAttendanceStatus(status);
    }

    public AbsenceRule checkAbsenceRule() {
        long totalExpulsionCount = countAttendanceStatus(AttendanceStatus.ABSENCE) + countAttendanceStatus(AttendanceStatus.LATE) / 3;
        return AbsenceRule.of(totalExpulsionCount);
    }

    @Override
    public int compareTo(final Crew crew) {
        long thisLate = this.countAttendanceStatus(AttendanceStatus.LATE);
        long otherLate = crew.countAttendanceStatus(AttendanceStatus.LATE);
        long thisTotalAbsence = crew.countAttendanceStatus(AttendanceStatus.ABSENCE) + thisLate / 3;
        long otherTotalAbsence = crew.countAttendanceStatus(AttendanceStatus.ABSENCE) + otherLate / 3;

        if(thisTotalAbsence != otherTotalAbsence) return Long.compare(otherTotalAbsence, thisTotalAbsence);
        if(thisLate % 3 != otherLate % 3) return Long.compare(otherLate % 3, thisLate % 3);
        return this.nickname.compareTo(crew.nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public List<Attendance> getAttendances() {
        return attendances.getAttendances();
    }
}
