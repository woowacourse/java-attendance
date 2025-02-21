package domain;

import constant.CampusConstant;
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
    private final Crews crews;

    public Attendance(Map<String, List<LocalDateTime>> attendance, LocalDate nowDate) {
        this.attendance = new HashMap<>();
        List<Crew> crews = new ArrayList<>();
        for (String name : attendance.keySet()) {
            Crew crew = new Crew(name);
            this.attendance.put(crew, new AttendanceTimes(attendance.get(name), nowDate));
            crews.add(crew);
        }
        this.crews = new Crews(crews);
    }

    public boolean isClosed(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(CampusConstant.CHRISTMAS);
    }

    public AttendanceTimes getAttendanceTimes(String name) {
        return attendance.get(crews.findCrew(name));
    }

    public void attend(String crewName, LocalDateTime attendanceDateTime) {
        validateAttended(crewName, attendanceDateTime);
        validateOpenHours(attendanceDateTime);
        attendance.get(crews.findCrew(crewName)).addAttendance(new AttendanceTime(attendanceDateTime));
    }

    public void edit(String crewName, int attendanceDay, LocalTime newAttendanceTime) {
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(CampusConstant.YEAR, CampusConstant.DECEMBER_MONTH, attendanceDay, newAttendanceTime.getHour(), newAttendanceTime.getMinute());
        validateOpenHours(newAttendanceDateTime);
        AttendanceTime attendanceTime = findAttendanceTime(crewName, LocalDate.of(CampusConstant.YEAR, CampusConstant.DECEMBER_MONTH, attendanceDay));
        attendanceTime.updateAttendanceDateTime(newAttendanceTime);
    }

    public List<String> checkExpelledCrew() {
        List<String> expelledCrew = new ArrayList<>();

        attendance.keySet().stream()
                .filter(crew -> crew.getExpelStatus(this.attendance.get(crew)))
                .forEach(crew -> expelledCrew.add(crew.getName()));

        return expelledCrew;
    }

    public AttendanceTime findAttendanceTime(String nickName, LocalDate attendanceDate) {
        AttendanceTimes attendanceTimes = this.attendance.get(crews.findCrew(nickName));
        return attendanceTimes.getAttendanceTime(attendanceDate);
    }

    public void validateNickName(String nickName) {
        crews.findCrew(nickName);
    }

    public int getAbsentCount(String nickName) {
        return this.attendance.get(crews.findCrew(nickName)).getAbsentCount();
    }

    public int getLateCountForSort(String nickName) {
        return this.attendance.get(crews.findCrew(nickName)).getLateCount() % CampusConstant.LATE_TO_ABSENT_UNIT;
    }

    public Map<AttendanceStatus, Integer> getCrewAttendanceStatus(String nickName) {
        Crew crew = crews.findCrew(nickName);
        return crew.getAttendanceStatus(attendance.get(crew));
    }

    private void validateAttended(String crewName, LocalDateTime attendanceDateTime) {
        if (checkAttended(crewName, attendanceDateTime.toLocalDate())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
        }
    }

    private void validateOpenHours(LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        LocalTime openHour = CampusConstant.CAMPUS_OPEN_TIME;
        LocalTime closeHour = CampusConstant.CAMPUS_CLOSE_TIME;
        if (attendanceTime.isBefore(openHour) || attendanceTime.isAfter(closeHour)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private boolean checkAttended(String crewName, LocalDate attendanceDate) {
        return attendance.get(crews.findCrew(crewName)).checkAttended(attendanceDate);
    }
}
