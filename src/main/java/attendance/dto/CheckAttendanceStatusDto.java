package attendance.dto;

import java.util.Map;

public record CheckAttendanceStatusDto(Map<String, Integer> statusCount) {}
