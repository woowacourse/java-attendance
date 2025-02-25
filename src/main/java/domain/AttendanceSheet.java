package domain;

import java.util.List;

public class AttendanceSheet {
    List<Attendance> attendances;

    public AttendanceSheet(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
