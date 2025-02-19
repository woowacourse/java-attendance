package view;

import controller.dto.AttendanceTimeDto;

public class Parser {

    public static AttendanceTimeDto parseAttendanceTime(String input) {
        String[] splitInput = input.split(":");
        if (splitInput.length != 2) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 올바르지 않습니다.");
        }

        int hour = parseInteger(splitInput[0]);
        int minute = parseInteger(splitInput[1]);

        return new AttendanceTimeDto(hour, minute);
    }

    public static Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 올바르지 않습니다.");
        }
    }
}
