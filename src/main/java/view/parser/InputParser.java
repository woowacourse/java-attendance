package view.parser;


import java.time.LocalTime;

public class InputParser {

    public LocalTime parseToLocalTime(String rawInputAttendanceTime) {
        String[] timeParts = rawInputAttendanceTime.split(":");
        int hour = parseToInt(timeParts[0]);
        int minute = parseToInt(timeParts[1]);
        return LocalTime.of(hour, minute);
    }

    public int parseToInt(String inputMenuNumber) {
        try {
            return Integer.parseInt(inputMenuNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자로 변환할 수 없는 입력값입니다");
        }
    }

    public String parseToTimeMessage(LocalTime time) {
        if (time == null) {
            return "--:--";
        }
        int hour = time.getHour();
        int minute = time.getMinute();

        return parseFullTime(hour) + ":" + parseFullTime(minute);
    }

    private String parseFullTime(int part) {
        if (part < 10) {
            return "0" + part;
        }
        return "" + part;
    }
}
