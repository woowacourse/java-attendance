package dto;

import domain.Attendance;
import java.util.Collections;
import java.util.List;

public record AttendanceLog(List<Attendance> sortedValue) {

    @Override
    public List<Attendance> sortedValue() {
        return Collections.unmodifiableList(sortedValue);
    }
}
