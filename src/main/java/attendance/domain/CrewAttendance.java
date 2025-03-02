package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CrewAttendance {
    private final Map<AttendanceRecord, AttendanceStatus> attendances;

    public CrewAttendance() {
        this.attendances = new HashMap<>();
    }

    public void add(final LocalDateTime attendance) {
        validateDuplicateDate(attendance);
        attendances.put(new AttendanceRecord(attendance), Campus.calculateAttendanceStatus(attendance));
    }

    private void validateDuplicateDate(final LocalDateTime attendance) {
        if (hasRecordAlready(attendance)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
    }

    public boolean hasRecordAlready(final LocalDateTime attendance) {
        return attendances.keySet().stream()
                .anyMatch(attendanceRecord -> attendanceRecord.date().equals(LocalDate.from(attendance)));
    }

    public void modify(final LocalDateTime newAttendance) {
        Entry<AttendanceRecord, AttendanceStatus> prevAttendance = getAttendanceOn(LocalDate.from(newAttendance));
        attendances.remove(prevAttendance.getKey());
        attendances.put(new AttendanceRecord(newAttendance), Campus.calculateAttendanceStatus(newAttendance));
    }

    public Entry<AttendanceRecord, AttendanceStatus> getAttendanceOn(final LocalDate date) {
        return attendances.entrySet().stream()
                .filter(entry -> entry.getKey().date().equals(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜의 출석 기록이 존재하지 않습니다."));
    }

    public Map<AttendanceRecord, AttendanceStatus> getAttendances() {
        return Collections.unmodifiableMap(attendances);
    }
}
