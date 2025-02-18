package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Attendance {

    private final Map<String, List<AttendanceTime>> attendance;

    public Attendance(Map<String, List<LocalDateTime>> attendance) {
        this.attendance = new HashMap<>();
        for (String name : attendance.keySet()) {
            this.attendance.put(name, attendance.get(name)
                    .stream()
                    .map(AttendanceTime::new)
                    .collect(Collectors.toList()));
        }
    }

    public void attend(String crewName, LocalDateTime attendanceTime) {
        checkAttended(crewName, attendanceTime);
        attendance.get(crewName).add(new AttendanceTime(attendanceTime));
    }

    private void checkAttended(String crewName, LocalDateTime attendanceTime) {
        List<AttendanceTime> attendancesOfCrew = attendance.get(crewName);
        for (AttendanceTime attendances : attendancesOfCrew) {
            if (attendances.checkSameDate(attendanceTime.toLocalDate())) {
                throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
            }
        }
    }
}
