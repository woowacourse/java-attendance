package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Attendances {
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

    public Attendance updateAttendance(LocalDateTime dateTime, int day) {
        if (dateTime.getDayOfMonth() > day) {
            throw new IllegalArgumentException("미래는 수정할 수 없습니다.");
        }
        Attendance attendance = new Attendance(dateTime);
        removeAttendance(day);
        records.add(attendance);
        return attendance;
    }

    private void removeAttendance(int day) {
        int removeIndex = 0;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).isSameDay(day)) {
                removeIndex = i;
                break;
            }
        }

        records.remove(removeIndex);
    }

    public CrewStatus calculateCrewStatus(LocalDate today) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = calculateAllAttendanceStatus(today);
        return CrewStatus.calculateCrewStatus(attendanceStatusCounts.get(AttendanceStatus.ABSENT)
                + attendanceStatusCounts.get(AttendanceStatus.LATE) / 3);
    }

    public Map<AttendanceStatus, Integer> calculateAllAttendanceStatus(LocalDate today) {
        Map<AttendanceStatus, Integer> attendanceStatusCount = initMap();
        for (Attendance attendance : records) {
            if (attendance.isBefore(today)) {
                AttendanceStatus attendanceStatus = attendance.calculateAttendanceStatus();
                attendanceStatusCount.put(attendanceStatus, attendanceStatusCount.get(attendanceStatus) + 1);
            }
        }

        return attendanceStatusCount;
    }

    private Map<AttendanceStatus, Integer> initMap() {
        EnumMap<AttendanceStatus, Integer> attedanceStatusCounts = new EnumMap<>(AttendanceStatus.class);
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attedanceStatusCounts.put(attendanceStatus, 0);
        }
        return attedanceStatusCounts;
    }

    public Attendance getAttendanceByDay(int day) {
        return records.stream()
                .filter(attendance -> attendance.getDateTime().getDayOfMonth() == day)
                .findFirst()
                .orElse(new Attendance(LocalDateTime.of(2024, 12, day, 23, 59)));
    }

    public List<Attendance> getRecords() {
        return records;
    }
}
