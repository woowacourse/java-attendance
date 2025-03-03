package model;

import java.util.Map;

public final class AttendanceCountsDto {
    private final Map<AttendanceStatus, Integer> map;

    public AttendanceCountsDto(final Map<AttendanceStatus, Integer> map) {
        this.map = map;
    }

    public Map<AttendanceStatus, Integer> map() {
        return map;
    }

    public Map<AttendanceStatus, Integer> getMap() {
        return map;
    }
}
