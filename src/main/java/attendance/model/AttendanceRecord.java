package attendance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record AttendanceRecord(
        List<AttendanceDateTime> attendanceDateTimes
) {
    public AttendanceRecord() {
        this(new ArrayList<>());
    }

    public void add(AttendanceDateTime attendanceDateTime) {
        attendanceDateTimes.add(attendanceDateTime);
    }

    public AttendanceDateTime findAttendanceByDate(LocalDate date) {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.equalsDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석일입니다."));
    }
}
