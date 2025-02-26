package attendance.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Util {

    public static int parseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("숫자가 아닙니다. 다시 입력해주세요.");
        }
    }

    public static LocalTime parseToTime(String str) {
        try {
            return LocalTime.parse(str, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 입력이 아닙니다. 다시 입력해주세요.");
        }
    }
}
