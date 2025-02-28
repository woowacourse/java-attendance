import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class InputParser {
    public static LocalDateTime parseTime(String input) {
        String[] splitInput = input.split(":");
        int hour = Integer.parseInt(splitInput[0]);
        int minute = Integer.parseInt(splitInput[1]);
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
    }

    public static String parseDateTimeToString(LocalDateTime dateTime) {
        return parseTimeToKorean(dateTime.toLocalDate()) + " " + parseTimeToKorean(dateTime.toLocalTime());
    }

    public static String parseAttendanceType(AttendanceType attendanceType) {
        Map<AttendanceType, String> valueOfAttendanceTypes = Map.of(AttendanceType.PRESENT, "출석",
                AttendanceType.LATE, "지각",
                AttendanceType.ABSENCE, "결석");
        return valueOfAttendanceTypes.get(attendanceType);
    }

    private static String parseTimeToKorean(LocalDate date) {
        String datePart = date.format(DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN));
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        return datePart + " " + dayOfWeek;
    }

    private static String parseTimeToKorean(LocalTime time) {
        if (time.getHour() == 0 && time.getMinute() == 0) {
            return "--:--";
        }
        return time.format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN));
    }
}
