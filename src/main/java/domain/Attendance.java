package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Attendance {

    private final Map<Crew, List<AttendanceTime>> attendance;

    public Attendance(Map<String, List<LocalDateTime>> attendance, LocalDate nowDate) {
        this.attendance = new HashMap<>();
        for (String name : attendance.keySet()) {
            this.attendance.put(new Crew(name), attendance.get(name)
                    .stream()
                    .map(AttendanceTime::new)
                    .collect(Collectors.toList()));
        }
        initializeAttendances(attendance, nowDate);
    }

    private void initializeAttendances(Map<String, List<LocalDateTime>> attendance, LocalDate nowDate) {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = nowDate;
        if (endDate.isAfter(LocalDate.of(2024, 12, 31))) {
            endDate = LocalDate.of(2025, 1, 1);
        }

        for (String name : attendance.keySet()) {
            initializeAttendance(name, startDate, endDate);
        }
    }

    private void initializeAttendance(String name, LocalDate startDate, LocalDate endDate) {
        List<AttendanceTime> attendanceTimes = this.attendance.get(findCrew(name));
        Set<LocalDate> attendanceDates = attendanceTimes.stream()
                .map(attendanceTime -> attendanceTime.getAttendanceDateTime().toLocalDate())
                .collect(Collectors.toSet());

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            attendanceTimes = addUnattended(attendanceDates, date, attendanceTimes);
        }
        this.attendance.put(findCrew(name), attendanceTimes);
    }

    private Crew findCrew(String name) {
        return attendance.keySet().stream()
                .filter(crew -> crew.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 크루입니다."));
    }

    private List<AttendanceTime> addUnattended(Set<LocalDate> attendanceDates, LocalDate date,
                                               List<AttendanceTime> attendanceTimes) {
        if (!(attendanceDates.contains(date) || isClosed(date))) {
            List<AttendanceTime> updatedAttendanceTimes = new ArrayList<>(attendanceTimes);
            updatedAttendanceTimes.add(new AttendanceTime(date, AttendanceStatus.UNATTEND));
            return updatedAttendanceTimes;
        }
        return attendanceTimes;
    }

    public boolean isClosed(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(
                LocalDate.of(2024, 12, 25));
    }

    public List<AttendanceTime> getAttendanceTimes(String name) {
        List<AttendanceTime> crewAttendances = this.attendance.getOrDefault(findCrew(name), new ArrayList<>());
        crewAttendances.sort(Comparator.comparing(AttendanceTime::getAttendanceDateTime));
        return crewAttendances;
    }

    public void attend(String crewName, LocalDateTime attendanceDateTime) {
        validateAttended(crewName, attendanceDateTime);
        validateOpenHours(attendanceDateTime);
        attendance.get(findCrew(crewName)).add(new AttendanceTime(attendanceDateTime));
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
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, attendanceDay, newAttendanceTime.getHour(),
                newAttendanceTime.getMinute());
        validateOpenHours(newAttendanceDateTime);
        AttendanceTime attendanceTime = findAttendanceTime(crewName, LocalDate.of(2024, 12, attendanceDay));
        attendanceTime.updateAttendanceDateTime(newAttendanceTime);
    }

    private boolean checkAttended(String crewName, LocalDate attendanceDate) {
        List<AttendanceTime> attendancesOfCrew = attendance.get(findCrew(crewName));
        for (AttendanceTime attendances : attendancesOfCrew) {
            if (attendances.checkAttended(attendanceDate)) {
                return true;
            }
        }
        return false;
    }

    public List<String> checkExpelledCrew() {
        List<String> expelledCrew = new ArrayList<>();

        attendance.keySet().stream()
                .filter(crew -> crew.getExpelStatus(this.getAttendanceTimes(crew.getName())))
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
        return getAttendanceTimes(nickName).stream()
                .filter(attendance -> attendance.getAttendanceDateTime().toLocalDate().equals(attendanceDate))
                .filter(attendance -> attendance.getAttendanceStatus() != AttendanceStatus.UNATTEND)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석하지 않은 날짜입니다."));
    }

    public void validateNickName(String nickName) {
        if (!this.attendance.containsKey(findCrew(nickName))) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않는 닉네임입니다.");
        }
    }

    public int getAbsentCount(String nickName) {
        int absentCount = (int) this.attendance.get(findCrew(nickName)).stream()
                .filter(e -> e.getAttendanceStatus().equals(AttendanceStatus.ABSENT) || e.getAttendanceStatus()
                        .equals(AttendanceStatus.UNATTEND))
                .count();
        int lateCount = (int) this.attendance.get(findCrew(nickName)).stream()
                .filter(e -> e.getAttendanceStatus().equals(AttendanceStatus.LATE))
                .count();

        return absentCount + lateCount / 3;
    }

    public int getLateCount(String nickName) {
        int lateCount = (int) this.attendance.get(findCrew(nickName)).stream()
                .filter(e -> e.getAttendanceStatus().equals(AttendanceStatus.LATE))
                .count();

        return lateCount % 3;
    }

    public Map<AttendanceStatus, Integer> getCrewAttendanceStatus(String nickName) {
        Crew crew = findCrew(nickName);
        return crew.getAttendanceStatus(attendance.get(crew));
    }
}
