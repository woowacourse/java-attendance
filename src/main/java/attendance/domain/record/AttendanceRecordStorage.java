package attendance.domain.record;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceRecordStorage {

    private final Map<String, AttendanceRecords> attendanceRecords = new HashMap<>();

    public void add(AttendanceRecord record) {
        AttendanceRecords records = findRecordsByNickname(record.getNickname());
        records.add(record);
        attendanceRecords.put(record.getNickname(), records);
    }

    public void update(AttendanceRecord newRecord) {
        remove(newRecord.getNickname(), newRecord.getDate());
        add(newRecord);
    }

    public Optional<AttendanceRecord> find(String nickname, LocalDate date) {
        AttendanceRecords records = findRecordsByNickname(nickname);
        return records.find(date);
    }

    public List<AttendanceRecord> findUnmodifiedRecordsByNickname(String nickname, int year, Month month) {
        AttendanceRecords records = findRecordsByNickname(nickname);
        return records.findRecordsInMonth(year, month);
    }

    public int calculateAttendanceCount(String nickname, LocalDate startDate, LocalDate endDate) {
        AttendanceRecords records = findRecordsByNickname(nickname);
        return records.calculateAttendanceRecordCount(startDate, endDate);
    }

    public int calculateLateCount(String nickname, LocalDate startDate, LocalDate endDate) {
        AttendanceRecords records = findRecordsByNickname(nickname);
        return records.calculateLateRecordCount(startDate, endDate);
    }

    private void remove(String nickname, LocalDate date) {
        AttendanceRecords records = findRecordsByNickname(nickname);
        records.remove(date);
        attendanceRecords.put(nickname, records);
    }

    private AttendanceRecords findRecordsByNickname(String nickname) {
        return attendanceRecords.getOrDefault(nickname, new AttendanceRecords());
    }
}
