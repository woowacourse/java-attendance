package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CrewAttendance {
    private final List<Attendance> attendances;

    public CrewAttendance() {
        this.attendances = new ArrayList<>();
    }

    public void add(final LocalDateTime attendance) {
        validateDuplicateDate(attendance);
        attendances.add(Attendance.of(attendance));
    }

    private void validateDuplicateDate(final LocalDateTime attendance) {
        if (isExistDay(attendance)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
    }

    public boolean isExistDay(final LocalDateTime targetDateTime) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDay(Attendance.of(targetDateTime)));
    }

    public void modify(final LocalDateTime newAttendance) {
        Attendance prevAttendance = getAttendanceOn(LocalDate.from(newAttendance));
        attendances.remove(prevAttendance);
        attendances.add(Attendance.of(newAttendance));
    }

    public Attendance getAttendanceOn(final LocalDateTime targetDay) {
        return getAttendanceOn(LocalDate.from(targetDay));
    }

    public Attendance getAttendanceOn(final LocalDate targetDay) {
        return attendances.stream()
                .filter(attendance -> attendance.record().date().equals(targetDay))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜의 출석 기록이 존재하지 않습니다."));
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatusBefore(final LocalDateTime today) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            if (attendanceStatus.equals(AttendanceStatus.ABSENT)) {
                int count = 0;
                for (int day = 1; day < today.getDayOfMonth(); day++) {
                    LocalDateTime targetDay = LocalDateTime.of(2024, 12, day, 0, 0);
                    if (!Campus.isOffDay(targetDay) && !isExistDay(targetDay)) {
                        count++;
                    }
                }
                attendanceStatusCounts.put(attendanceStatus, count);
            } else {
                int count = (int) attendances.stream()
                        .filter(attendance -> !attendance.isSameDay(Attendance.of(today)))
                        .filter(attendance -> attendance.status().equals(attendanceStatus))
                        .count();
                attendanceStatusCounts.put(attendanceStatus, count);
            }
        }

        return attendanceStatusCounts;
    }
}
