package attendance.domain;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Crew {
    private final String name;
    private final AttendanceHistory attendanceHistory;

    public Crew(String name) {
        this.name =  name;
        this.attendanceHistory = new AttendanceHistory();
    }

    public void addAttendanceResult(AttendanceResult attendanceResult) {
        attendanceHistory.addAttendanceResult(attendanceResult);
    }

    public String getName() {
        return name;
    }

    public AttendanceHistory getAttendanceHistory() {
         return attendanceHistory;
    }
}
