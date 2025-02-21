package dto;

import domain.Attendance;
import java.util.List;

public class AttendanceData {
    private final List<Attendance> value;

    public AttendanceData(List<Attendance> value) {
        this.value = value;
    }

    public List<Attendance> getValue() {
        return value;
    }
}
