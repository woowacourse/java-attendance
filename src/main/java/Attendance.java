import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Attendance {

    public AttendanceStatus checkAttendanceStatus(String nickname, LocalDateTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if (time.getDayOfWeek() == DayOfWeek.MONDAY) {
            return attend(hour, minute, 13);
        }

        return  attend(hour, minute, 10);
    }

    private AttendanceStatus attend(int hour, int minute, int startHour) {
        if (hour >= startHour) {
            if (hour > startHour || minute > 30) {
                return AttendanceStatus.ABSENCE;
            }
            if (minute > 5) {
                return AttendanceStatus.LATE;
            }
        }
        return AttendanceStatus.ATTEND;
    }
}
