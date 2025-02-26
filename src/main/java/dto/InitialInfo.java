package dto;

import domain.AttendanceRecord;
import domain.CrewName;
import java.util.Map;

public record InitialInfo(Map<CrewName, AttendanceRecord> value) {
}
