package attendance.domain;

import static attendance.domain.exception.CrewExceptionMessage.NOT_EXIST_UPDATE_ATTENDANCE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Crew implements Comparable<Crew> {
    private static final int LATE_TO_ABSENCE_UNIT = 3;

    private final String nickname;
    private final Attendances attendances;
    private AttendanceStatusCounts attendanceStatusCounts;

    public Crew(final String nickname) {
        this.nickname = nickname;
        this.attendances = new Attendances();
        this.attendanceStatusCounts = new AttendanceStatusCounts();
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

    public List<Attendance> getCrewAttendancesUtilYesterday(final LocalDate today) {
        List<Attendance> attendancesUtilYesterday = new ArrayList<>();

        LocalDate date = today.withDayOfMonth(1);
        while (date.isBefore(today)) {
            if (!Holiday.checkHoliday(date.atStartOfDay())) {
                attendancesUtilYesterday.add(findAttendanceForDate(date));
            }
            date = date.plusDays(1);
        }
        return attendancesUtilYesterday;
    }

    public AbsenceRule checkAbsenceRule() {
        Map<AttendanceStatus, Long> statusCounts = attendanceStatusCounts.getAttendanceStatusCounts();
        long totalExpulsionCount =
                statusCounts.get(AttendanceStatus.ABSENCE) + statusCounts.get(AttendanceStatus.LATE) / 3;
        return AbsenceRule.of(totalExpulsionCount);
    }

    public void countAttendanceStatus(LocalDate today) {
        Attendances attendancesUntilYesterday = new Attendances(getCrewAttendancesUtilYesterday(today));
        Map<AttendanceStatus, Long> statusCounts = new EnumMap<>(AttendanceStatus.class);
        for (AttendanceStatus status : AttendanceStatus.values()) {
            statusCounts.put(status, attendancesUntilYesterday.countAttendanceStatus(status));
        }
        attendanceStatusCounts = new AttendanceStatusCounts(statusCounts);
    }

    public long getAttendanceCount(AttendanceStatus attendanceStatus) {
        Map<AttendanceStatus, Long> statusCounts = attendanceStatusCounts.getAttendanceStatusCounts();
        if (AttendanceStatus.ATTEND.equals(attendanceStatus)) {
            return statusCounts.get(AttendanceStatus.ATTEND);
        }
        if (AttendanceStatus.LATE.equals(attendanceStatus)) {
            return statusCounts.get(AttendanceStatus.LATE);
        }
        if (AttendanceStatus.ABSENCE.equals(attendanceStatus)) {
            return statusCounts.get(AttendanceStatus.ABSENCE);
        }
        return 0L;
    }

    private Attendance findAttendanceForDate(final LocalDate date) {
        return attendances.getAttendances().stream()
                .filter(crewAttendance -> crewAttendance.isEqualDate(date))
                .findFirst()
                .orElseGet(() -> new Attendance(LocalDateTime.of(date, LocalTime.MIN)));
    }

    @Override
    public int compareTo(final Crew crew) {
        Map<AttendanceStatus, Long> thisStatusCounts = this.attendanceStatusCounts.getAttendanceStatusCounts();
        Map<AttendanceStatus, Long> otherStatusCounts = crew.attendanceStatusCounts.getAttendanceStatusCounts();
        long thisLate = thisStatusCounts.get(AttendanceStatus.LATE);
        long otherLate = otherStatusCounts.get(AttendanceStatus.LATE);
        long thisTotalAbsence = thisStatusCounts.get(AttendanceStatus.ABSENCE) + thisLate / LATE_TO_ABSENCE_UNIT;
        long otherTotalAbsence = otherStatusCounts.get(AttendanceStatus.ABSENCE) + otherLate / LATE_TO_ABSENCE_UNIT;

        if (thisTotalAbsence != otherTotalAbsence) {
            return Long.compare(otherTotalAbsence, thisTotalAbsence);
        }
        if (thisLate % LATE_TO_ABSENCE_UNIT != otherLate % LATE_TO_ABSENCE_UNIT) {
            return Long.compare(otherLate % LATE_TO_ABSENCE_UNIT, thisLate % LATE_TO_ABSENCE_UNIT);
        }
        return this.nickname.compareTo(crew.nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public List<Attendance> getAttendances() {
        return attendances.getAttendances();
    }
}
