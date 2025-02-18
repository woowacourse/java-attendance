import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {

    public String check(LocalDateTime today) {
        LocalDate date = today.toLocalDate();
        LocalTime time = today.toLocalTime();

        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int hour = time.getHour();
        int minute = time.getMinute();

        if(month == 12 && day == 25){
            throw new IllegalArgumentException("[ERROR] 공휴일에는 출석할 수 없습니다.");
        }

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 출석할 수 없습니다.");
        }

        String koreanDayOfWeek = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);

        String attendanceState = decisionAttendance(dayOfWeek, hour, minute).description;

        return String.format("%d월 %02d일 %s %d:%d (%s)", month, day, koreanDayOfWeek, hour, minute, attendanceState);
    }

    private AttendanceState decisionAttendance(DayOfWeek dayOfWeek, int hour, int minute) {
        if (hour < 8 || hour >= 23) {
            throw new IllegalArgumentException("[ERROR] 출석 시간이 아닙니다.");
        }

        if (dayOfWeek == DayOfWeek.MONDAY) {
            return decisionByHour(hour, minute, 13);
        }
        return decisionByHour(hour, minute, 10);
    }

    private AttendanceState decisionByHour(int hour, int minute, int standardHour) {
        if (hour == standardHour) {
            return decisionByMinute(minute);
        }

        if (hour > standardHour) {
            return AttendanceState.ABSENT;
        }

        return AttendanceState.ATTEND;
    }

    private AttendanceState decisionByMinute(int minute) {
        if (minute > 30) {
            return AttendanceState.ABSENT;
        }

        if (minute > 5) {
            return AttendanceState.LATE;
        }

        return AttendanceState.ATTEND;
    }
}
