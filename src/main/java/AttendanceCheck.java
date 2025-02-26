import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceCheck {
    static final LocalTime START_TIME = LocalTime.of(10, 0);

    Map<String, Map<DayOfWeek, LocalTime>> attendanceRecord = new HashMap<>();

    public void attend(DayOfWeek dayOfWeek, String nickname, LocalTime attendanceTime) {
        if (!attendanceRecord.containsKey(nickname) || !attendanceRecord.get(nickname).containsKey(dayOfWeek)) {
            Map<DayOfWeek, LocalTime> attendance = Map.of(dayOfWeek, attendanceTime);
            attendanceRecord.put(nickname, attendance);
            return;
        }

        throw new IllegalStateException("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
    }

    public LocalTime getAttendanceTime(DayOfWeek dayOfWeek, String nickname) {
        return attendanceRecord.get(nickname).get(dayOfWeek);
    }

    public String getAttendanceStatus(DayOfWeek dayOfWeek, String nickname) {
        if (DayOfWeek.MONDAY.equals(dayOfWeek)) {
            if (LocalTime.of(13, 0).plusMinutes(30).isBefore(attendanceRecord.get(nickname).get(dayOfWeek))) {
                return "결석";
            }

            if (LocalTime.of(13, 0).plusMinutes(5).isBefore(attendanceRecord.get(nickname).get(dayOfWeek))) {
                return "지각";
            }
            return "출석";
        }

        if (START_TIME.plusMinutes(30).isBefore(attendanceRecord.get(nickname).get(dayOfWeek))) {
            return "결석";
        }

        if (START_TIME.plusMinutes(5).isBefore(attendanceRecord.get(nickname).get(dayOfWeek))) {
            return "지각";
        }

        return "출석";
    }

}
