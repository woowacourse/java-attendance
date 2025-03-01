package model;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AttendanceTime {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");

    private final LocalTime time;

    private AttendanceTime(final LocalTime time) {
        this.time = time;
    }

    public static AttendanceTime of(final String timeInput) {
        final LocalTime parsedTime = parse(timeInput);
        return new AttendanceTime(parsedTime);
    }

    public static AttendanceTime of(final int hour, final int minute) {
        try {
            final LocalTime time = LocalTime.of(hour, minute);
            return new AttendanceTime(time);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("AttendanceTime은 시간 범위 내의 입력값이 들어와야 합니다");
        }
    }

    public static AttendanceTime of(final LocalTime time) {
        return new AttendanceTime(time);
    }

    private static LocalTime parse(final String timeInput) {
        try {
            return LocalTime.parse(timeInput, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("AttendanceTime 의 형식은 H:m 로 들어와야 합니다");
        }
    }

    public boolean isBeforeToPlus(final AttendanceDateTime attendanceDateTime, final int plusMinutes) {
        return getTime().plusMinutes(plusMinutes)
                .isBefore(attendanceDateTime.getTime());
    }

    public LocalTime getTime() {
        return time;
    }
}
