package dto;

import domain.CrewName;

public record AttendanceCount(CrewName crewName, int attendCount, int lateCount, int absentCount, int consideredAbsentCount) {
}
