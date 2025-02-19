package attendance.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Crew {
    private final String name;
    private final List<AttendanceResult> attendanceHistory = new ArrayList<>();

    public Crew(String name) {
        this.name =  name;
    }

    public String getName() {
        return name;
    }

    public void addAttendanceHistory(AttendanceResult attendanceResult) {
        attendanceHistory.add(attendanceResult);
    }

    public List<AttendanceResult> getAttendanceHistory() {
        return Collections.unmodifiableList(attendanceHistory);
    }
}
