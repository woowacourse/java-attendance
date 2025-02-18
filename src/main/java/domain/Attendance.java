package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        for (String name : attendance.keySet() ) {
            Set<LocalDate> attendanceDates = this.attendance.get(name).stream()
                    .map(attendanceTime -> attendanceTime.getAttendanceDateTime().toLocalDate())
                    .collect(Collectors.toSet());

            List<AttendanceTime> attendanceTimes = this.attendance.get(name);
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                if (attendanceDates.contains(date) || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(LocalDate.of(2024, 12, 25))) {
                    continue;
                }
                attendanceTimes.add(new AttendanceTime(date, AttendanceStatus.ABSENT));
            }
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

    public Map<AttendanceStatus, Integer> countAttendanceStatus(String name) {
        Map<AttendanceStatus, Integer> attendanceStatuses = new HashMap<>();
        List<AttendanceTime> attendanceTimes = getAttendanceTimes(name);
        for (AttendanceTime attendanceTime : attendanceTimes) {
            attendanceStatuses.putIfAbsent(attendanceTime.getAttendanceStatus(), 0);
            attendanceStatuses.put(attendanceTime.getAttendanceStatus(),
                    attendanceStatuses.get(attendanceTime.getAttendanceStatus()) + 1);
        }
        return attendanceStatuses;
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
