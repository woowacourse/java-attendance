package console;

import crew.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import type.AttendanceType;
import type.AttendanceTypeCount;
import type.PenaltyType;

public class Parser {
    public static LocalTime parseTime(String input) {
        String[] splitInput = input.split(":");
        int hour = Integer.parseInt(splitInput[0]);
        int minute = Integer.parseInt(splitInput[1]);
        return LocalTime.of(hour, minute);
    }

    public static String parseDateTimeToString(LocalDateTime dateTime) {
        return parseDateToString(dateTime.toLocalDate()) + " " + parseTimeToString(dateTime.toLocalTime());
    }

    public static String parseAttendanceType(AttendanceType attendanceType) {
        Map<AttendanceType, String> valueOfAttendanceTypes = Map.of(AttendanceType.PRESENT, "출석",
                AttendanceType.LATE, "지각",
                AttendanceType.ABSENCE, "결석",
                AttendanceType.NO_DATA, "결석");
        return valueOfAttendanceTypes.get(attendanceType);
    }

    public static String parsePenaltyType(PenaltyType penaltyType) {
        Map<PenaltyType, String> valueOfPenaltyTypes = Map.of(PenaltyType.ONE_ON_ONE, "면담",
                PenaltyType.BAN, "제적",
                PenaltyType.WARNING, "경고");
        return valueOfPenaltyTypes.get(penaltyType);
    }

    public static String parseDateToString(LocalDate date) {
        String datePart = date.format(DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN));
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        return datePart + " " + dayOfWeek;
    }

    public static String parseTimeToString(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN));
    }

    public static String parseExpulsionCandidate(Crew crew, AttendanceTypeCount attendanceTypeCount) {
        PenaltyType penaltyType = PenaltyType.findByAbsenceCount(attendanceTypeCount.getAdjustedAbsenceCount());

        return "- " + crew.getName() + ": " + "결석 " + attendanceTypeCount.getAbsenceCount() + "회, 지각 "
                + attendanceTypeCount.getLateCount() + "회 (" + parsePenaltyType(penaltyType) + ")";

    }

    public static int parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력하셔야 합니다.");
        }
    }
}
