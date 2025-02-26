package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<String, List<Attendance>> attendanceRecord;

    public Attendances() {
        this.attendanceRecord = new HashMap<>();
    }

    public void addAttendance(String name, Attendance attendance) {
        addName(name);
        List<Attendance> attendances = attendanceRecord.computeIfAbsent(name, k -> new ArrayList<>());
        attendances.add(attendance);
    }

    private void addName(String name) {
        if (attendanceRecord.containsKey(name)) return;

        attendanceRecord.put(name, new ArrayList<Attendance>());
    }

    public boolean hasAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        List<Attendance> attendances = attendanceRecord.get(name);
        return attendances.stream()
            .anyMatch(attendance -> attendance.hasAttend(attendanceDate, attendanceTime));
    }

    public void checkNameExists(String name) {
        if (!attendanceRecord.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }
}
