package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceStatusRecord {
    private final Map<LocalDate, AttendanceStatus> attendanceStatusRecords;

    public AttendanceStatusRecord(List<LocalDateTime> localDateTimes) {
        Map<LocalDate, AttendanceStatus> map = new HashMap<>();
        for (LocalDateTime localDateTime : localDateTimes) {
            LocalDate localDate = LocalDate.from(localDateTime);
            LocalTime localTime = LocalTime.from(localDateTime);
            map.put(localDate, AttendanceStatus.calculateAttendanceStatus(localDate, localTime));
        }
        this.attendanceStatusRecords = map;
    }

    public long findAttendanceStatusCount(AttendanceStatus attendanceStatus) {
        return attendanceStatusRecords.entrySet().stream()
                .filter(record -> record.getValue().equals(attendanceStatus))
                .count();
    }

    public void modifyAttendanceStatusRecord(LocalDate localDate, AttendanceStatus attendanceStatus) {
        attendanceStatusRecords.put(localDate, attendanceStatus);
    }

    public void registerAttendanceStatusRecord(LocalDate todayDate, AttendanceStatus attendanceStatus) {
        attendanceStatusRecords.put(todayDate, attendanceStatus);
    }

    public void putAttendanceStateToAbsent(LocalDate localDate) {
        attendanceStatusRecords.put(localDate, AttendanceStatus.ABSENT);
    }

    public Map<LocalDate, AttendanceStatus> getAttendanceStatusRecords() {
        return attendanceStatusRecords;
    }
}
