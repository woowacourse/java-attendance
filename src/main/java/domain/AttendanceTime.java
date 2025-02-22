package domain;

import error.CustomIllegalArgumentException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import util.Constants;

public class AttendanceTime {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
    private final LocalTime localTime;

    private AttendanceTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public static AttendanceTime of(final String inputTime) {
        final LocalTime localTime = parseTime(inputTime);
        return new AttendanceTime(localTime);
    }

    private static LocalTime parseTime(final String inputTime) {
        try {
            return LocalTime.parse(inputTime, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgumentException("올바른 시간 형식이 아닙니다. 얘를들어 10:22 이런 형태로 작성해주세요.");
        }
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
