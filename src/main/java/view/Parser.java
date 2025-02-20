package view;

import controller.dto.AttendanceTimeDto;
import domain.attendance.PenaltyType;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class Parser {

    public static AttendanceTimeDto parseAttendanceTime(String input) {
        String[] splitInput = input.split(":");
        if (splitInput.length != 2) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }

        int hour = parseInteger(splitInput[0]);
        int minute = parseInteger(splitInput[1]);

        return new AttendanceTimeDto(hour, minute);
    }

    public static Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    public static String parseDateFormat(int month, int day) {
        return String.format("%02d월 %02d일", month, day);
    }

    public static String parseTimeFormat(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    public static String parseDayOfWeek(int dayOfWeek) {
        return DayOfWeek.of(dayOfWeek).getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String parsePenaltyTypeFormat(PenaltyType penaltyType) {
        if (penaltyType == PenaltyType.BAN) {
            return "제적";
        }
        if (penaltyType == PenaltyType.ONE_ON_ONE) {
            return "면담";
        }

        return "경고";
    }
}
