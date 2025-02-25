package attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {

    private final String name;
    private final Map<LocalDate, LocalTime> attendanceRecords = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void attendance(LocalDate date, LocalTime time) {
        validateAttendanceDate(date);
        attendanceRecords.put(date, time);
    }

    private void validateAttendanceDate(LocalDate date) {
        if (attendanceRecords.containsKey(date)) {
            throw new IllegalArgumentException("이미 출석한 경우 다시 출석할 수 없습니다. 출석 수정 기능을 이용해 주세요.");
        }
    }

    public void modifyAttendance(LocalDate date, LocalTime time) {
        validateModifyAttendanceDate(date);
        attendanceRecords.put(date, time);
    }

    private void validateModifyAttendanceDate(LocalDate date) {
        if (!attendanceRecords.containsKey(date)) {
            throw new IllegalArgumentException();
        }
    }

    public LocalTime getAttendanceTimeOf(LocalDate date) {
        return attendanceRecords.get(date);
    }

    public AttendanceStatus getAttendanceStatusOf(LocalDate date) {
        if (!attendanceRecords.containsKey(date) && !Holiday.isHoliday()) {
            return AttendanceStatus.ABSENCE;
        }
        return AttendanceStatus.from(date, attendanceRecords.get(date));
    }
}
