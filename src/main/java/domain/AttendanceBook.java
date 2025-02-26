package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<CrewName, AttendanceRecord> value;

    public AttendanceBook() {
        this.value = new HashMap<>();
    }
}
