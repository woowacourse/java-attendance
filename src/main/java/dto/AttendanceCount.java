package dto;

import util.Constants;

public record AttendanceCount(int attendCount, int lateCount, int absentCount, int consideredAbsentCount) {
}
