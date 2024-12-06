package attendance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserInputPaser {

    public static LocalDateTime parseAttendanceTime(String inputAttendanceTime) {
        String[] attendanceTime = inputAttendanceTime.split(":");
        int nowDayOfMonth = LocalDate.now().getDayOfMonth();
        return LocalDateTime.of(2024, 12, nowDayOfMonth,
            Integer.parseInt(attendanceTime[0]), Integer.parseInt(attendanceTime[1]));
    }

}
