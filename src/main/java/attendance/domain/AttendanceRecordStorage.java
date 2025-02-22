package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceRecordStorage {

    private final Map<String, List<AttendanceRecord>> attendanceRecords = new HashMap<>();

    public void add(AttendanceRecord record) {
        if (!record.isExpulsion()) {
            List<AttendanceRecord> records = findRecordsByNickname(record.getNickname());
            records.add(record);
            attendanceRecords.put(record.getNickname(), records);
        }
    }

    public void update(AttendanceRecord newRecord) {
        remove(newRecord.getNickname(), newRecord.getDate());
        add(newRecord);
    }

    public Optional<AttendanceRecord> find(String nickname, LocalDate date) {
        List<AttendanceRecord> records = findRecordsByNickname(nickname);
        return records.stream().filter(record -> record.checkSameDate(date))
                .findAny();
    }

    private void remove(String nickname, LocalDate date) {
        Optional<AttendanceRecord> originRecord = find(nickname, date);
        if (originRecord.isPresent()) {
            List<AttendanceRecord> originRecords = attendanceRecords.getOrDefault(nickname, new ArrayList<>());
            originRecords.remove(originRecord.get());
        }
    }

    private List<AttendanceRecord> findRecordsByNickname(String nickname) {
        return attendanceRecords.getOrDefault(nickname, new ArrayList<>());
    }
}
