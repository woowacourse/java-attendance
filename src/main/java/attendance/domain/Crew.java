package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crew implements Comparable<Crew> {

    private final String nickname;
    private final List<Attendance> attendances;
    private final Map<AttendanceStatus, Integer> statusCount;

    public Crew(final String nickname, final List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = attendances;
        statusCount = new HashMap<>();
        attendances.forEach((attendance) -> addStatusCount(attendance, 1));
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
        statusCount.put(attendance.getStatus(), statusCount.getOrDefault(attendance.getStatus(), 0) + 1);
    }

    public void addStatusCount(Attendance attendance, int amount) {
        statusCount.put(attendance.getStatus(), statusCount.getOrDefault(attendance.getStatus(), 0) + amount);
    }

    public Warning checkWarning() {
        return Warning.check(calculateTotalAbsenceCount());
    }

    private int calculateTotalAbsenceCount() {
        return statusCount.getOrDefault(AttendanceStatus.ABSENCE, 0)
                + statusCount.getOrDefault(AttendanceStatus.LATE_ABSENCE, 0)
                + statusCount.getOrDefault(AttendanceStatus.LATE, 0) / 3;
    }

    public Attendance updateAttendance(final LocalDateTime dateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(LocalDate.from(dateTime)))
                .findFirst()
                .map(attendance -> {
                    AttendanceStatus before = attendance.getStatus();
                    AttendanceStatus after = attendance.updateDateTime(dateTime);
                    updateStatusCount(before, after);
                    return attendance;
                })
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없습니다."));
    }

    private void updateStatusCount(AttendanceStatus before, AttendanceStatus after) {
        statusCount.put(before, statusCount.getOrDefault(before, 0) - 1);
        statusCount.put(after, statusCount.getOrDefault(after, 0) + 1);
    }

    public Attendance findAttendanceByDate(final LocalDate updateDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(updateDate))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public int compareTo(Crew o) {
        int targetAbsenceCount = o.calculateTotalAbsenceCount();
        int targetLateCount = o.statusCount
                .getOrDefault(AttendanceStatus.LATE, 0) % 3;

        int absenceCount = calculateTotalAbsenceCount();
        int lateCount = statusCount.getOrDefault(AttendanceStatus.LATE, 0) % 3;

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

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public Map<AttendanceStatus, Integer> getStatusCount() {
        return statusCount;
    }
}
