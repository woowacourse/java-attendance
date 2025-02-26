import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceCheck {
    static Map<String, LocalTime> attendanceRecord = new HashMap<>();

    public static void attend(String nickname, LocalTime attendanceTime) {
        attendanceRecord.put(nickname, attendanceTime);
    }

    public static LocalTime getAttendanceTime(String nickname) {
        return attendanceRecord.get(nickname);
    }

    public static String getAttendanceStatus(String nickname) {
        attendanceRecord.get(nickname);
        return "출석";
    }

}
