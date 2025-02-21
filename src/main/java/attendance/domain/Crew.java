package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Crew implements Comparable<Crew> {
    public static final int LATE_TO_ABSENCE_UNIT = 3;

    private final String nickname;
    private final List<Attendance> attendances;

    public Crew(final String nickname, final List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = attendances;
    }

    public boolean isEqualToNickname(final String nickname) {
        return this.nickname.equals(nickname);
    }

    public void existInAttendances(final LocalDate date) {
        attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(date))
                .findAny()
                .ifPresent((attendance) -> {
                    throw new IllegalArgumentException("[ERROR] 이미 오늘 출석을 하셨습니다. 출석 수정을 이용해주세요.");
                });
    }

    public void addAttendance(final Attendance attendance) {
        attendances.add(attendance);
    }

    public Warning checkWarning() {
        return Warning.check(calculateTotalAbsenceCount());
    }

    private long calculateTotalAbsenceCount() {
        return countAbsence() + (countLate() / LATE_TO_ABSENCE_UNIT);
    }

    public long countAttend() {
        return attendances.stream()
                .filter(attendance -> attendance.getStatus().equals(AttendanceStatus.ATTEND))
                .count();
    }

    public long countAbsence() {
        return attendances.stream()
                .filter(attendance -> {
                    AttendanceStatus status = attendance.getStatus();
                    return status.equals(AttendanceStatus.ABSENCE) || status.equals(AttendanceStatus.LATE_ABSENCE);
                })
                .count();
    }

    public long countLate() {
        return attendances.stream()
                .filter(attendance -> attendance.getStatus().equals(AttendanceStatus.LATE))
                .count();
    }

    public Attendance updateAttendance(final LocalDateTime dateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(LocalDate.from(dateTime)))
                .findFirst()
                .map(attendance -> attendance.updateDateTime(dateTime))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없습니다."));
    }

    public Attendance findAttendanceByDate(final LocalDate updateDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(updateDate))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public int compareTo(Crew comparisonCrew) {
        long targetAbsenceCount = comparisonCrew.calculateTotalAbsenceCount();
        long targetLateCount = comparisonCrew.countLate() % LATE_TO_ABSENCE_UNIT;

        long absenceCount = this.calculateTotalAbsenceCount();
        long lateCount = this.countLate() % LATE_TO_ABSENCE_UNIT;

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
        return nickname.compareTo(comparisonCrew.getNickname());
    }

    public String getNickname() {
        return nickname;
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }
}
