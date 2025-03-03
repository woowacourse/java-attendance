package dto;

import domain.AttendanceRecord;
import domain.CrewName;
import java.util.Map;

public record InitialInformation(Map<CrewName, AttendanceRecord> initialInformation) {
}
