package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> records = new ArrayList<>();

    public void addAttendance(LocalDateTime dateTime) {
        Attendance attendance = new Attendance(dateTime);
        records.remove(attendance);
        records.add(attendance);
    }

    public List<Attendance> getRecords() {
        return records;
    }
}
