import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class InputParser {
    public static LocalTime parseTime(String input) {
        String[] splitInput = input.split(":");
        int hour = Integer.parseInt(splitInput[0]);
        int minute = Integer.parseInt(splitInput[1]);
        return LocalTime.of(hour, minute);
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

    public static String parsePenaltyType(PenaltyType penaltyType) {
        Map<PenaltyType, String> valueOfPenaltyTypes = Map.of(PenaltyType.ONE_ON_ONE, "면담",
                PenaltyType.BAN, "제적",
                PenaltyType.WARNING, "경고");
        return valueOfPenaltyTypes.get(penaltyType);
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

    public static String parseExpulsionCandidate(Crew crew, AttendanceTypeCount attendanceTypeCount) {
        PenaltyType penaltyType = PenaltyType.findByAbsenceCount(attendanceTypeCount.getAdjustedAbsenceCount());

        return "- " + crew.getName() + ": " + "결석 " + attendanceTypeCount.getAbsenceCount() + "회, 지각 "
                + attendanceTypeCount.getLateCount() + "회 (" + parsePenaltyType(penaltyType) + ")";

    }
}
