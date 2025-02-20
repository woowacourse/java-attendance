package attendance.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AttendanceHistoryManager {
    private final Set<AttendanceHistory> attendanceHistories = new HashSet<>();

    public void addAttendanceResult(AttendanceHistory attendanceHistory) {
        if (!attendanceHistories.add(attendanceHistory)) {
            throw new IllegalArgumentException("해당 날짜에 이미 출석하셨습니다.");
        }
    }

    public Set<AttendanceHistory> getAttendanceHistories() {
        return Collections.unmodifiableSet(attendanceHistories);
    }

    public AttendanceHistory getAttendanceHistory(AttendanceHistory modifyAttendanceHistory) {
        return attendanceHistories.stream()
                .filter(result -> result.equals(modifyAttendanceHistory))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public void modifyAttendanceResult(AttendanceHistory modifyAttendanceHistory) {
        AttendanceHistory attendanceHistory = attendanceHistories.stream()
                .filter(result -> result.equals(modifyAttendanceHistory))
                .findAny()
                .get();
        attendanceHistory.modify(modifyAttendanceHistory);
    }
}
