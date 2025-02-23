package domain;

import error.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Attendances {

    private final TreeSet<Attendance> attendances;

    public Attendances(final TreeSet<Attendance> attendances) {
        this.attendances = attendances;
    }

    // TODO:: 로직 더 공부하기
    public attendanceStatusCounts calculateAttendanceCount() {
        Map<AttendanceStatus, Long> counts = attendances.stream()
                .collect(Collectors.groupingBy(
                        Attendance::getAttendanceStatus,
                        Collectors.counting()
                ));

        final long attendanceCount = counts.getOrDefault(AttendanceStatus.ATTENDANCE, 0L);
        final long tardinessCount = counts.getOrDefault(AttendanceStatus.TARDINESS, 0L);
        final long absenceCount = counts.getOrDefault(AttendanceStatus.ABSENCE, 0L);

        return new attendanceStatusCounts((int) attendanceCount, (int) tardinessCount, (int) absenceCount);
    }

    public boolean isAttended(final LocalDateTime dateTime) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.equalsToLocalDateTime(dateTime));
    }

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendance(final LocalDateTime dateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.hasSameDate(dateTime.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException("수정하는 일자를 찾을 수 없습니다."));
    }

    public List<Integer> getDayOfMonth() {
        return attendances.stream()
                .map(Attendance::getDayOfMonth)
                .toList();
    }

    public List<AttendanceSummary> getAttendanceSummary() {
        return attendances.stream()
                .map(Attendance::getSummary)
                .toList();
    }

    public void remove(final Attendance oldAttendance) {
        attendances.remove(oldAttendance);
    }
}
