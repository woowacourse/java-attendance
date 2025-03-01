package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

    public AttendanceTime modifyAttendance(AttendanceTime attendanceTime, LocalDateTime modifyTime)  {
        attendanceTimes.remove(attendanceTime);
        AttendanceTime modifyAttendanceTime = AttendanceTime.from(modifyTime);
        add(modifyAttendanceTime);
        return modifyAttendanceTime;
    }

    public AttendanceTime findAttendanceByDate(int findDate) {
        return attendanceTimes.stream()
            .filter(result -> result.isSameDate(findDate))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("해당 날짜에는 출석 기록이 없습니다."));
    }

    public boolean hasAttendanceOnDate(LocalDate findDate) {
        return attendanceTimes.stream()
            .anyMatch(result -> result.hasDate(findDate));
    }

    public List<AttendanceTime> getAttendanceTimes() {
        List<AttendanceTime> sortAttendanceTimes = new ArrayList<>(attendanceTimes);
        Collections.sort(sortAttendanceTimes, Comparator.comparing(AttendanceTime::getAttendanceDateTime));
        return Collections.unmodifiableList(sortAttendanceTimes);
    }
}
