package dto;

import domain.AttendanceRecord;
import domain.CrewName;
import java.util.Map;

public class InitialInfo {
    private final Map<CrewName, AttendanceRecord> value;

    public InitialInfo(Map<CrewName, AttendanceRecord> value) {
        this.value = value;
    }

    public Map<CrewName, AttendanceRecord> getValue() {
        return value;
    }
}
