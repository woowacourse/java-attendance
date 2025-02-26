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

    public boolean containsAttendanceDateTimeByDate(LocalDate localDate) {
        return attendanceDateTimes.stream()
                .anyMatch(attendanceDateTime -> attendanceDateTime.equalsDate(localDate));
    }

    public AttendanceDateTime findAttendanceByDate(LocalDate date) {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.equalsDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석일입니다."));
    }

    public long computeLateCount() {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> AttendanceStatus.from(
                        attendanceDateTime.getAttendanceTime(),
                        EducationSchedule.from(attendanceDateTime.getAttendanceDate())
                ).equals(AttendanceStatus.LATE)).count();
    }
}
