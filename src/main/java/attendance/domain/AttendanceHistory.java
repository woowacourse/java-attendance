package attendance.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AttendanceHistory {
    private final Set<AttendanceResult> attendanceHistory = new HashSet<>();

    public void addAttendanceResult(AttendanceResult attendanceResult) {
        if (!attendanceHistory.add(attendanceResult)) {
            throw new IllegalArgumentException("해당 날짜에 이미 출석하셨습니다.");
        }
    }

    public Set<AttendanceResult> getAttendanceHistory() {
        return Collections.unmodifiableSet(attendanceHistory);
    }
}
