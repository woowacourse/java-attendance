package attendance.model;

import static attendance.error.ErrorMessage.ERROR_NAME_LENGTH;

import java.time.LocalDate;

public class Crew {

    private final String name;
    private final AttendanceHistory attendanceHistory;

    public Crew(String name) {
        validate(name);
        this.name = name;
        this.attendanceHistory = new AttendanceHistory();
    }

    private void validate(String name) {
        if (name == null || name.isBlank() || name.length() > 4) {
            throw new IllegalArgumentException(ERROR_NAME_LENGTH);
        }
    }

    public String getName() {
        return name;
    }

    public AttendanceHistory getAttendanceHistory() {
        return attendanceHistory;
    }

    public AttendanceDetail findAttendanceDetail(LocalDate findDate) {
        return attendanceHistory.findAttendanceDetail(findDate);
    }

    public void attend(AttendanceDetail attendanceDetail) {
        attendanceHistory.addAttendanceDetail(attendanceDetail);
    }
}
