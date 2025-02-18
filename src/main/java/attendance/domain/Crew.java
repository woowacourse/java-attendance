package attendance.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Crew {
    private final String name;
    private final List<AttendanceHistory> attendanceHistories = new ArrayList<>();

    public Crew(String name) {
        this.name =  name;
    }

    public String getName() {
        return name;
    }

    public void addAttendanceHistory(AttendanceHistory attendanceHistory) {
        attendanceHistories.add(attendanceHistory);
    }

    public List<AttendanceHistory> getAttendanceHistories() {
        return Collections.unmodifiableList(attendanceHistories);
    }
}
