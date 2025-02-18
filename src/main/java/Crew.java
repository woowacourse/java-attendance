import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {

    private final String nickname;
    private final Map<LocalDate, LocalTime> attendanceTimes = new HashMap<>();

    public Crew(String name) {
        this.nickname = name;
    }

    public void attendance(LocalDate date, LocalTime time) {
        validateAlreadyAttendanceDate(date);
        attendanceTimes.put(date, time);
    }

    private void validateAlreadyAttendanceDate(LocalDate date) {
        if (attendanceTimes.containsKey(date)) {
            throw new AlreadyAttendanceException("이미 출석 처리되어 있습니다. 수정 기능을 이용해주세요.");
        }
    }

    public String getName() {
        return nickname;
    }

    public void modifyAttendance(LocalDate date, LocalTime time) {
        attendanceTimes.put(date, time);
    }

    public LocalTime getAttendanceTimeByDate(LocalDate date) {
        return attendanceTimes.get(date);
    }

    public AttendanceStatus getAttendanceStatusByDate(LocalDate date) {
        return AttendanceStatus.of(date, attendanceTimes.get(date));
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatusStatistics(LocalDate today) {
        Map<AttendanceStatus, Integer> result = new HashMap<>();
        for(int day = 1; day < today.getDayOfMonth() - 1; day++) {
            AttendanceStatus attendanceStatus = getAttendanceStatusByDate(LocalDate.of(today.getYear(),
                today.getMonth(), day));
            result.put(attendanceStatus, result.getOrDefault(attendanceStatus, 0) + 1);
        }
        return result;
    }
}
