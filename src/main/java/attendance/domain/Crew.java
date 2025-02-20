package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Crew {
    private final String name;
    private final AttendanceHistoryManager attendanceHistoryManager;

    public Crew(String name) {
        this.name =  name;
        this.attendanceHistoryManager = new AttendanceHistoryManager();
    }

    public void addAttendanceResult(AttendanceHistory attendanceHistory) {
        attendanceHistoryManager.addAttendanceResult(attendanceHistory);
    }

    public AttendanceHistory modifyAttendanceResult(AttendanceHistory attendanceHistory, LocalTime localTime) {
        return attendanceHistoryManager.modifyAttendanceResult(attendanceHistory, localTime);
    }


    public String getName() {
        return name;
    }

    public AttendanceHistory getAttendanceHistory(LocalDate localDate) {
        return attendanceHistoryManager.getAttendanceHistory(localDate);
    }
}
