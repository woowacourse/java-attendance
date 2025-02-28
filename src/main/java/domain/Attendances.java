package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Attendances {
    private static final int ABSENT_HOUR = 23;
    private static final int ABSENT_MINUTE = 59;
    private final List<Attendance> records = new ArrayList<>();

    public Attendance addAttendance(LocalDateTime dateTime) {
        Attendance attendance = new Attendance(dateTime);
        if (containSameDay(attendance)) {
            throw new UnsupportedOperationException("오늘 이미 출석하셨습니다. 출석 수정을 이용해주세요");
        }
        records.add(attendance);

        return attendance;
    }

    private boolean containSameDay(Attendance attendance) {
        return records.stream()
                .anyMatch(attendance1 -> attendance1.isSameDay(attendance));
    }

    public Attendance updateAttendance(LocalDateTime dateTime, int today) {
        if (dateTime.getDayOfMonth() > today) {
            throw new IllegalArgumentException("미래는 수정할 수 없습니다.");
        }
        Attendance attendance = new Attendance(dateTime);
        records.removeIf(record -> record.isSameDay(dateTime.getDayOfMonth()));
        records.add(attendance);
        return attendance;
    }

    public CrewStatus calculateCrewStatus(LocalDate today) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = calculateAllAttendanceStatus(today);
        return CrewStatus.calculateCrewStatus(attendanceStatusCounts.get(AttendanceStatus.ABSENT)
                + attendanceStatusCounts.get(AttendanceStatus.LATE) / 3);
    }

    public Map<AttendanceStatus, Integer> calculateAllAttendanceStatus(LocalDate today) {
        Map<AttendanceStatus, Integer> attendanceStatusCount = initMap();
        records.stream()
                .filter(attendance -> attendance.isBefore(today))
                .map(Attendance::calculateAttendanceStatus)
                .forEach(status ->
                        attendanceStatusCount.compute(status, (key, value) -> value + 1));

        return attendanceStatusCount;
    }

    private Map<AttendanceStatus, Integer> initMap() {
        EnumMap<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attendanceStatusCounts.put(attendanceStatus, 0);
        }
        return attendanceStatusCounts;
    }

    public Attendance getAttendanceByDay(int day) {
        return records.stream()
                .filter(attendance -> attendance.getDateTime().getDayOfMonth() == day)
                .findFirst()
                .orElse(new Attendance(LocalDateTime.of(2024, 12, day, ABSENT_HOUR, ABSENT_MINUTE)));
    }

    public List<Attendance> getRecords() {
        return records;
    }
}
