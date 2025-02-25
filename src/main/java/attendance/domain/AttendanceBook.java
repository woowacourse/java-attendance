package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, List<AttendanceRecord>> crewRecords = new HashMap<>();

    public void add(String name, AttendanceRecord record) {
        List<AttendanceRecord> records = crewRecords.computeIfAbsent(name, key -> new ArrayList<>());
        records.add(record);
    }

    public void modify(String crewName, LocalDate targetDate, LocalTime modifyTime) {
        AttendanceRecord record = getRecordBy(crewName, targetDate);
        record.modify(modifyTime);
    }

    public AttendanceRecord getRecordBy(String crewName, LocalDate targetDate) {
        List<AttendanceRecord> records = crewRecords.get(crewName);
        return records.stream()
                .filter(record -> record.isSameDate(targetDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 없는 날짜입니다."));
    }

    public List<AttendanceRecord> getRecordsByName(String name) {
        return Collections.unmodifiableList(crewRecords.get(name));
    }
}
