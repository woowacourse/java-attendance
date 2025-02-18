package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public List<AttendanceTime> getAttendanceTimes(String name) {
        return this.attendance.getOrDefault(name, new ArrayList<>());
    }

    public void attend(String crewName, LocalDateTime attendanceTime) {
        if (checkAttended(crewName, attendanceTime.toLocalDate())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
        };
        attendance.get(crewName).add(new AttendanceTime(attendanceTime));
    }

    public void edit(String crewName, int attendanceDay, LocalTime newAttendanceTime) {
        AttendanceTime attendanceTime = getAttendanceTimes(crewName).stream()
                .filter(attendance -> attendance.getAttendanceDateTime().getDayOfMonth() == attendanceDay)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석하지 않은 날짜입니다."));

        attendanceTime.updateAttendanceDateTime(newAttendanceTime);

    }

    private boolean checkAttended(String crewName, LocalDate attendanceDate) {
        List<AttendanceTime> attendancesOfCrew = attendance.get(crewName);
        for (AttendanceTime attendances : attendancesOfCrew) {
            if (attendances.checkSameDate(attendanceDate)) {
                return true;
            }
        }
        return false;
    }
}
