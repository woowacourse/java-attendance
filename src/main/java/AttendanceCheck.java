import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceCheck {
    static final LocalTime START_TIME = LocalTime.of(10, 0);
    Map<String, LocalTime> attendanceRecord = new HashMap<>();

    public void attend(String nickname, LocalTime attendanceTime) {
        if (!attendanceRecord.containsKey(nickname)) {
            attendanceRecord.put(nickname, attendanceTime);
            return;
        }
        throw new IllegalStateException("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");

    }

    public LocalTime getAttendanceTime(String nickname) {
        return attendanceRecord.get(nickname);
    }

    public String getAttendanceStatus(String nickname) {
        if (START_TIME.plusMinutes(30).isBefore(attendanceRecord.get(nickname))) {
            return "결석";
        }

        if (START_TIME.plusMinutes(5).isBefore(attendanceRecord.get(nickname))) {
            return "지각";
        }

        return "출석";
    }

}
