package attendance.domain.attendanceBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import attendance.common.exception.AttendanceArgumentException;

public class AttendanceList {
    private static final String CANT_FIND_INFO = "출석 정보를 찾을 수 없습니다.";
    private final List<Attendance> attendances = new ArrayList<>();

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendance(Attendance attendance) {
        return attendances.stream()
            .filter((i) -> i.equals(attendance))
            .findFirst()
            .orElseThrow(() -> new AttendanceArgumentException(CANT_FIND_INFO));
    }

    public Attendance findAttendance(LocalDate date) {
        return attendances.stream()
            .filter((i) -> i.isEqualDate(date))
            .findFirst()
            .orElseThrow(() -> new AttendanceArgumentException(CANT_FIND_INFO));
    }

    public void remove(Attendance attendance) {
        attendances.remove(attendance);
    }

    public boolean contains(Attendance attendance) {
        var date = attendance.dateTime().toLocalDate();
        return attendances.stream()
            .anyMatch(o -> o.dateTime().toLocalDate().equals(date));
    }
}
