package domain;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CampusTime {

    private final LocalTime time;

    private CampusTime(String inputTime) {
        validateTimeRangeAndType(inputTime);
        this.time = LocalTime.parse(inputTime);
    }

    public static CampusTime from(String inputTime) {
        return new CampusTime(inputTime);
    }

    private void validateTimeRangeAndType(String hourColonMinute) {
        try {
            LocalTime.parse(hourColonMinute);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
        }
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }
}
