package attendance.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public record AttendanceRegister(
        Map<String, AttendanceHistory> register
) {
    public AttendanceHistory findAttendanceHistoryByCrewName(String crewName) {
        if (!register.containsKey(crewName)) {
            throw new IllegalArgumentException("존재하지 않는 크루원 입니다.");
        }
        return register.get(crewName);
    }

    public void attend(String crewName, AttendanceDateTime attendanceDateTime) {
        AttendanceHistory attendanceHistory = register.getOrDefault(crewName, new AttendanceHistory(new ArrayList<>()));
        attendanceHistory.addAttendanceDateTime(attendanceDateTime);
        register.put(crewName, attendanceHistory);
    }

    public Stream<Entry<String, AttendanceHistory>> entryStream() {
        return register.entrySet().stream();
    }
}
