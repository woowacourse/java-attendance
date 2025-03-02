package model;

import java.util.Map;

public record AttendanceCountsDto(Map<AttendanceStatus, Integer> map) {

}

