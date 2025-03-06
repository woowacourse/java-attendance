package attendance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserInputParser {
    public static LocalDateTime parseAttendanceTime(String inputAttendanceTime) {
        String[] attendanceTime = inputAttendanceTime.split(":");
        int nowDayOfMonth = LocalDate.now().getDayOfMonth();
        return LocalDateTime.of(2024, 12, nowDayOfMonth,
            Integer.parseInt(attendanceTime[0]), Integer.parseInt(attendanceTime[1]));
    }

    public static LocalDateTime parseModifyDateTime(String inputModifyDate, String inputModifyTime) {
        String[] attendanceTime = inputModifyTime.split(":");
        return LocalDateTime.of(2024, 12, Integer.parseInt(inputModifyDate), Integer.parseInt(attendanceTime[0]), Integer.parseInt(attendanceTime[1]));
    }
}
