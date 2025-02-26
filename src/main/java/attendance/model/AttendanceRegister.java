package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceRegister {
    private final Map<String, List<LocalDateTime>> register = new HashMap<>();

    public void attend(String name, LocalDateTime localDateTime) {
        List<LocalDateTime> attendanceRecord = register.getOrDefault(name, new ArrayList<>());
        attendanceRecord.add(localDateTime);
        register.putIfAbsent(name, attendanceRecord);
    }

    public LocalDateTime findAttendanceByName(String name, LocalDate localDate) {
        return register.get(name).stream()
                .filter(localDateTime -> localDateTime.toLocalDate().equals(localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석일입니다."));
    }
}
