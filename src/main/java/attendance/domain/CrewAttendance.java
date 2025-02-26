package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CrewAttendance {
    private final String name;
    private final Map<LocalDate, AttendanceTimeStatus> attendances;

    public CrewAttendance(String name) {
        this.name = name;
        this.attendances = new HashMap<>();
    }

    public void add(final LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();
        AttendanceTimeStatus attendanceTimeStatus = new AttendanceTimeStatus(localDateTime);

        if (attendances.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
        }
        attendances.put(date, attendanceTimeStatus);
    }

    public void modify(final LocalDateTime localDateTime) {
        AttendanceTimeStatus attendanceTimeStatus = new AttendanceTimeStatus(localDateTime);
        attendances.put(localDateTime.toLocalDate(), attendanceTimeStatus);
    }

    public AttendanceTimeStatus getAttendanceOn(final LocalDate date) {
        if (isEmptyOn(date)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
        }
        return attendances.get(date);
    }

    private boolean isEmptyOn(final LocalDate localDate) {
        return !attendances.containsKey(localDate);
    }

    public boolean hasSameWarningLevel(WarningLevel warningLevel, final LocalDate today) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = countAttendanceStatusBefore(today);
        WarningLevel crewWarningLevel = WarningLevel.calculateLevel(attendanceStatusCounts);
        return crewWarningLevel == warningLevel;
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatusBefore(LocalDate localDate) {
        Map<LocalDate, AttendanceTimeStatus> attendances = getAttendancesBefore(localDate);
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            long count = attendances.values().stream()
                    .filter(timeStatus -> timeStatus.status().equals(attendanceStatus))
                    .count();
            attendanceStatusCounts.put(attendanceStatus, (int) count);
        }
        return attendanceStatusCounts;
    }

    public Map<LocalDate, AttendanceTimeStatus> getAttendancesBefore(LocalDate localDate) {
        updateAttendanceBefore(localDate);
        return attendances.entrySet().stream()
                .filter(entry -> entry.getKey().isBefore(localDate))
                .collect(Collectors.toUnmodifiableMap(Entry::getKey, Entry::getValue));
    }

    private void updateAttendanceBefore(LocalDate endDay) {
        for (int day = 1; day < endDay.getDayOfMonth(); day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            addAbsenceIfEmptyOn(date);
        }
    }

    private void addAbsenceIfEmptyOn(final LocalDate date) {
        if (AttendanceChecker.isCampusDay(date) && isEmptyOn(date)) {
            attendances.put(date, new AttendanceTimeStatus());
        }
    }

    public String getName() {
        return name;
    }
}
