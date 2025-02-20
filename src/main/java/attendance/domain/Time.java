package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.List;

public class Time {
    private final String hour;
    private final String minute;

    private static final String COLON = ":";

    private Time(String hour, String minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static Time from(final String time) {
        List<String> timeSplit = validateColon(time);
        String hour = timeSplit.get(0);
        String minute = timeSplit.get(1);
        validateHourNumber(hour);
        validateMinuteNumber(minute);
        return new Time(hour, minute);
    }

    public static Time makeAbsentValue() {

        return new Time("--", "--");
    }

    private static void validateHourNumber(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException numberFormatException) {
            throw CustomException.from(ErrorMessage.NUMBER_FORMAT_HOUR_ERROR);
        }
    }

    private static void validateMinuteNumber(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException numberFormatException) {
            throw CustomException.from(ErrorMessage.NUMBER_FORMAT_MINUTE_ERROR);
        }
    }

    private static List<String> validateColon(String time) {
        List<String> dividedTimes = List.of(time.split(COLON));
        if (dividedTimes.size() != 2) {
            throw CustomException.from(ErrorMessage.COLON_ERROR);
        }
        return dividedTimes;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

}
