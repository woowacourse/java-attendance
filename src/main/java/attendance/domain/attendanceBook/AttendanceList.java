package attendance.domain.attendanceBook;

import java.util.ArrayList;
import java.util.List;

import attendance.common.exception.AttendanceArgumentException;

public record AttendanceList(List<Attendance> attendances) {
    private static final String CANT_FIND_INFO = "출석 정보를 찾을 수 없습니다.";

    public AttendanceList() {
        this(new ArrayList<>());
    }

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendance(Attendance attendance) {
        return attendances.stream()
            .filter((i) -> i.equals(attendance))
            .findFirst()
            .orElseThrow(() -> new AttendanceArgumentException(CANT_FIND_INFO));
    }

    public boolean contains(Attendance attendance) {
        var date = attendance.dateTime().toLocalDate();
        return attendances.stream()
            .anyMatch(o -> o.dateTime().toLocalDate().equals(date));
    }
}
