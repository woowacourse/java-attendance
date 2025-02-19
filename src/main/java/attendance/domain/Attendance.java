package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Attendance {
    private final String name;
    private final Map<LocalDate, HourMinute> timestamps;

    public Attendance(String name) {
        this.name = name;
        this.timestamps = new HashMap<>();
    }

    public void add(final LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();
        HourMinute hourMinute = new HourMinute(localDateTime);

        if (timestamps.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
        }

        timestamps.put(date, hourMinute);
    }

    public boolean isNameMatch(String anotherName) {
        return this.name.equals(anotherName);
    }

    public HourMinute modify(final LocalDate localDate, final HourMinute hourMinute) {
        return timestamps.put(localDate, hourMinute);
    }

    public boolean hasTimeStamp(final LocalDate localDate) {
        return timestamps.containsKey(localDate);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(final int today) {
        updateTimestamp(today);

        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            long count = timestamps.values().stream()
                    .filter(hourMinute -> hourMinute.attendanceStatus().equals(attendanceStatus))
                    .count();

            attendanceStatuses.put(attendanceStatus, (int) count);
        }
        AttendanceStatus status = timestamps.get(LocalDate.of(2024, 12, today)).attendanceStatus();

        attendanceStatuses.put(status, attendanceStatuses.get(status) - 1);

        return attendanceStatuses;
    }

    private void updateTimestamp(int today) {
        for(int day = 1 ; day < today ; day++){
            LocalDate date = LocalDate.of(2024, 12, day);
            if(!AttendanceChecker.isCampusDay(day)){
                continue;
            }

            if (!timestamps.containsKey(date)){
                timestamps.put(date, new HourMinute(-1, -1, AttendanceStatus.ABSENCE));
            }
        }
    }
}
