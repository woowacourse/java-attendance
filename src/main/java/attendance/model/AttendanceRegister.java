package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRegister {
    private final Map<String, AttendanceRecord> register = new HashMap<>();

    public void attend(String crewName, AttendanceDateTime attendanceDateTime) {
        AttendanceRecord attendanceRecord = register.getOrDefault(crewName, new AttendanceRecord());
        attendanceRecord.add(attendanceDateTime);
        register.putIfAbsent(crewName, attendanceRecord);
    }

    public AttendanceDateTime findAttendanceDateTimeByCrewName(String crewName, LocalDate attendanceDate) {
        validateContainsCrewName(crewName);
        return register.get(crewName).findAttendanceByDate(attendanceDate);
    }

    private void validateContainsCrewName(String name) {
        if (!register.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }

    public void modify(String crewName, LocalDate modifyDate, LocalTime modifyTime) {
        validateContainsCrewName(crewName);
        AttendanceRecord attendanceRecord = register.get(crewName);
        AttendanceDateTime foundAttendanceDateTime = attendanceRecord.findAttendanceByDate(modifyDate);
        foundAttendanceDateTime.modifyAttendanceTime(modifyTime);
    }
}
