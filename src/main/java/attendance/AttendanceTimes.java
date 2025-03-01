package attendance;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AttendanceTimes {

    private final Set<AttendanceTime> attendanceTimes;

    private AttendanceTimes() {
        this.attendanceTimes = new HashSet<>();
    }

    public static AttendanceTimes create() {
        return new AttendanceTimes();
    }

    public boolean add(AttendanceTime attendanceDateTime) {
        return attendanceTimes.add(attendanceDateTime);
    }

    public AttendanceTime findAttendanceByDate(int findDate) {
        return attendanceTimes.stream()
            .filter(result -> result.isSameDate(findDate))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("해당 날짜에는 출석 기록이 없습니다."));
    }

    public Set<AttendanceTime> getAttendanceTimes() {
        return Collections.unmodifiableSet(attendanceTimes);
    }
}
