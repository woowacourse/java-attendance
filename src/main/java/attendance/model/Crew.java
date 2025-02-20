package attendance.model;

import java.time.LocalDate;
import java.util.stream.Stream;

public class Crew {

    private final String name;
    private final AttendanceHistory attendanceHistory;

    public Crew(String name) {
        validate(name);
        this.name = name;
        this.attendanceHistory = new AttendanceHistory();
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("크루의 이름은 1자 이상 5자 이하여야 합니다.");
        }
        if (name.length() >= 5) {
            throw new IllegalArgumentException("크루의 이름은 1자 이상 5자 이하여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public AttendanceHistory getAttendanceHistory() {
        return attendanceHistory;
    }

    public AttendanceDetail findAttendanceDetail(LocalDate localDate) {
        return attendanceHistory.findAttendanceDetail(localDate);
    }

    public Stream<AttendanceDetail> attendanceHistoryStream() {
        return attendanceHistory.stream();
    }

    public void attend(AttendanceDetail attendanceDetail) {
        attendanceHistory.addAttendanceDetail(attendanceDetail);
    }
}
