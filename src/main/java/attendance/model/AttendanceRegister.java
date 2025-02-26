package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceRegister {
    private final Map<String, List<AttendanceDateTime>> register = new HashMap<>();

    public void attend(String crewName, AttendanceDateTime attendanceDateTime) {
        List<AttendanceDateTime> attendanceRecord = register.getOrDefault(crewName, new ArrayList<>());
        attendanceRecord.add(attendanceDateTime);
        register.putIfAbsent(crewName, attendanceRecord);
    }

    public AttendanceDateTime findAttendanceByCrewName(String crewName, LocalDate attendanceDate) {
        validateContainsCrewName(crewName);
        return register.get(crewName).stream()
                .filter(attendanceDateTime -> attendanceDateTime.equalsDate(attendanceDate))
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
        List<AttendanceDateTime> attendanceRecord = register.get(crewName);
        AttendanceDateTime foundAttendanceDateTime = attendanceRecord.stream()
                .filter(attendanceDateTime -> attendanceDateTime.equalsDate(modifyDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석일입니다."));
        foundAttendanceDateTime.modifyAttendanceTime(modifyTime);
    }
}
