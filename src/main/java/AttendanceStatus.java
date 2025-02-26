import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE, LATE, ABSENT;

    public static AttendanceStatus attendanceStatusCalculate(LocalDate todayDate ,String input) {
        DayOfWeek todayDayOfWeek = todayDate.getDayOfWeek();
        LocalTime localDate = LocalTime.parse(input);
        AttendanceStatus absent = mondayAttendanceStatusCalculate(todayDayOfWeek, localDate);
        if (absent != null) {
            return absent;
        }
        if (localDate.isAfter(LocalTime.of(10, 30))){
            return ABSENT;
        }
        if (localDate.isAfter(LocalTime.of(10, 5))){
            return LATE;
        }
        return ATTENDANCE;
    }

    private static AttendanceStatus mondayAttendanceStatusCalculate(DayOfWeek todayDayOfWeek, LocalTime localDate) {
        if (todayDayOfWeek.equals(DayOfWeek.MONDAY)){
            if (localDate.isAfter(LocalTime.of(13, 30))){
                return ABSENT;
            }
            if (localDate.isAfter(LocalTime.of(13, 5))){
                return LATE;
            }
            return ATTENDANCE;
        }
        return null;
    }
}
