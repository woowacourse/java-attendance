package domain;

import controller.AttendanceCommandController;
import error.CustomIllegalArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import util.Constants;

public class AttendanceDateTime {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private final LocalDateTime localDateTime;

    private AttendanceDateTime(final LocalDateTime localDateTime) {
        validateHoliday(localDateTime);
        this.localDateTime = localDateTime;
    }

    public static AttendanceDateTime of(final String inputDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
            return new AttendanceDateTime(dateTime);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgumentException("올바른 날짜 및 시간형식이 아닙니다.");
        }
    }

    public static AttendanceDateTime of(final LocalDateTime localDateTime) {
        return new AttendanceDateTime(localDateTime);
    }

    public static AttendanceDateTime of(final String desiredUpdateDate, final String desiredUpdateTime) {
        final LocalDate localDate = parseLocalDate(desiredUpdateDate);
        final LocalTime localTime = parseLocalTime(desiredUpdateTime);

        return new AttendanceDateTime(LocalDateTime.of(localDate, localTime));
    }

    public static AttendanceDateTime generateTodayAttendance(final AttendanceTime attendanceTime) {
        final LocalDate localDate = AttendanceCommandController.FIX_DATE_TIME.toLocalDate();
        final LocalTime localTime = attendanceTime.getLocalTime();

        return new AttendanceDateTime(LocalDateTime.of(localDate, localTime));
    }

    private static LocalDate parseLocalDate(final String inputDate) {
        try {
            return LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, Integer.parseInt(inputDate));
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new CustomIllegalArgumentException("올바른 형식이 아닙니다.");
        }
    }

    private static LocalTime parseLocalTime(final String inputTime) {
        try {
            return LocalTime.parse(inputTime);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgumentException("올바른 형식이 아닙니다.");
        }
    }

    private void validateHoliday(LocalDateTime localDateTime) {
        if (Constants.HOLIDAYS.contains(localDateTime.getDayOfMonth())) {
            throw new CustomIllegalArgumentException(
                    String.format(localDateTime.format(Week.NON_SCHOOL_DAY_FORMAT) + "은 등교일이 아닙니다."));
        }
    }

    public LocalTime toLocalTime() {
        return localDateTime.toLocalTime();
    }

    public int getDayOfMonth() {
        return localDateTime.getDayOfMonth();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
