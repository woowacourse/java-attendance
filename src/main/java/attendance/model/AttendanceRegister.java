package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceRegister {
    private final Map<String, List<LocalDateTime>> register = new HashMap<>();

    public void attend(String crewName, LocalDateTime localDateTime) {
        List<LocalDateTime> attendanceRecord = register.getOrDefault(crewName, new ArrayList<>());
        attendanceRecord.add(localDateTime);
        register.putIfAbsent(crewName, attendanceRecord);
    }

    public LocalDateTime findAttendanceByCrewName(String crewName, LocalDate localDate) {
        validateContainsCrewName(crewName);
        return register.get(crewName).stream()
                .filter(localDateTime -> localDateTime.toLocalDate().equals(localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석일입니다."));
    }

    private void validateContainsCrewName(String name) {
        if (!register.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }

    public void modify(String crewName, LocalDate modifyDate, LocalTime modifyTime) {
        validateContainsCrewName(crewName);
        List<LocalDateTime> attendanceRecord = register.get(crewName);
        int index = -1;
        for (int i = 0; i < attendanceRecord.size(); ++i) {
            if (attendanceRecord.get(i).toLocalDate().equals(modifyDate)) {
                index = i;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("존재하지 않는 출석일입니다.");
        }
        attendanceRecord.remove(index);
        attendanceRecord.add(LocalDateTime.of(modifyDate, modifyTime));
    }
}
