package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {

    private final Map<Crew, AttendanceTimes> attendance;

    public Attendance(Map<String, List<LocalDateTime>> attendance, LocalDate nowDate) {
        this.attendance = new HashMap<>();
        for (String name : attendance.keySet()) {
            this.attendance.put(new Crew(name), new AttendanceTimes(attendance.get(name), nowDate));
        }
    }

    private Crew findCrew(String name) {
        return attendance.keySet().stream()
                .filter(crew -> crew.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 크루입니다."));
    }

    public boolean isClosed(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(LocalDate.of(2024, 12, 25));
    }

    public AttendanceTimes getAttendanceTimes(String name) {
        return attendance.get(findCrew(name));
    }

    public void attend(String crewName, LocalDateTime attendanceDateTime) {
        validateAttended(crewName, attendanceDateTime);
        validateOpenHours(attendanceDateTime);
        attendance.get(findCrew(crewName)).addAttendance(new AttendanceTime(attendanceDateTime));
    }

    private void validateOpenHours(LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        LocalTime openHour = LocalTime.of(8, 0);
        LocalTime closeHour = LocalTime.of(23, 0);
        if (attendanceTime.isBefore(openHour) || attendanceTime.isAfter(closeHour)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private void validateAttended(String crewName, LocalDateTime attendanceDateTime) {
        if (checkAttended(crewName, attendanceDateTime.toLocalDate())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
        }
    }

    public void edit(String crewName, int attendanceDay, LocalTime newAttendanceTime) {
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, attendanceDay, newAttendanceTime.getHour(), newAttendanceTime.getMinute());
        validateOpenHours(newAttendanceDateTime);
        AttendanceTime attendanceTime = findAttendanceTime(crewName, LocalDate.of(2024, 12, attendanceDay));
        attendanceTime.updateAttendanceDateTime(newAttendanceTime);
    }

    private boolean checkAttended(String crewName, LocalDate attendanceDate) {
        return attendance.get(findCrew(crewName)).checkAttended(attendanceDate);
    }

    public List<String> checkExpelledCrew() {
        List<String> expelledCrew = new ArrayList<>();

        attendance.keySet().stream()
                .filter(crew -> crew.getExpelStatus(this.attendance.get(crew)))
                .forEach(crew -> expelledCrew.add(crew.getName()));

        return expelledCrew;
    }

    public LocalDateTime getAttendanceDateTime(String nickName, LocalDate attendanceDate) {
        AttendanceTime attendanceTime = findAttendanceTime(nickName, attendanceDate);
        return attendanceTime.getAttendanceDateTime();
    }

    public AttendanceStatus getAttendanceStatus(String nickName, LocalDate attendanceDate) {
        AttendanceTime attendanceTime = findAttendanceTime(nickName, attendanceDate);
        return attendanceTime.getAttendanceStatus();
    }

    public AttendanceTime findAttendanceTime(String nickName, LocalDate attendanceDate) {
        AttendanceTimes attendanceTimes = this.attendance.get(findCrew(nickName));
        return attendanceTimes.getAttendanceTime(attendanceDate);
    }

    public void validateNickName(String nickName) {
        if (!this.attendance.containsKey(findCrew(nickName))) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않는 닉네임입니다.");
        }
    }

    public int getAbsentCount(String nickName) {
        return this.attendance.get(findCrew(nickName)).getAbsentCount();
    }

    public int getLateCountForSort(String nickName) {
        return this.attendance.get(findCrew(nickName)).getLateCount() % 3;
    }

    public Map<AttendanceStatus, Integer> getCrewAttendanceStatus(String nickName) {
        Crew crew = findCrew(nickName);
        return crew.getAttendanceStatus(attendance.get(crew));
    }
}
