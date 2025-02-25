package attendance.domain;

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

    public List<AttendanceRecord> getRecordsByName(String name) {
        return Collections.unmodifiableList(crewRecords.get(name));
    }
}
