package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {

    private final List<Attendance> attendances;

    public AttendanceBook(Attendance... attendance) {
        this.attendances = new ArrayList<>(List.of(attendance));
    }

    public void attend(Attendance attendance) {

    }
}
