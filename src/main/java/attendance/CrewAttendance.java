package attendance;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CrewAttendance {
    private final Map<AttendanceRecord, AttendanceStatus> attendances;

    public CrewAttendance() {
        this.attendances = new HashMap<>();
    }

    public void add(final LocalDateTime attendance) {
        if (hasRecord(attendance)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
        attendances.put(new AttendanceRecord(attendance), AttendanceStatus.ABSENT);
    }

    public boolean hasRecord(final LocalDateTime attendance) {
        return attendances.containsKey(new AttendanceRecord(attendance));
    }
}
