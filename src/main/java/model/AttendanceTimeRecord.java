package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceTimeRecord {
    private final Map<LocalDate, LocalTime> attendanceTimeRecords;

    public AttendanceTimeRecord(List<LocalDateTime> localDateTimes) {
        Map<LocalDate, LocalTime> map = new HashMap<>();
        for (LocalDateTime localDateTime : localDateTimes) {
            map.put(LocalDate.from(localDateTime), LocalTime.from(localDateTime));
        }
        this.attendanceTimeRecords = map;
    }

    public void putAttendanceTimeRecord(LocalDate localDate, LocalTime localTime){
        attendanceTimeRecords.put(localDate, localTime);
    }

    public boolean checkAttendanceRecordByLocalDate(LocalDate localDate) {
        return attendanceTimeRecords.get(localDate) != null;
    }

    public void validateDuplicateAttendance(LocalDate today) {
        if (checkAttendanceRecordByLocalDate(today)) {
            throw new IllegalArgumentException("[ERROR] 출석기록이 존재합니다.");
        }
    }

    public Map<LocalDate, LocalTime> getAttendanceTimeRecords() {
        return attendanceTimeRecords;
    }
}
