package domain;

import static util.parser.DateTimeParser.parseStringToTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Coach {

    private static final String NOT_OPERATING_TIME_ERROR_MESSAGE = "캠퍼스 운영 시간이 아닙니다.";
    private static final String HOLIDAY_ERROR_MESSAGE = "휴일입니다.";

    private final AttendanceBook attendanceBook;
    private final LocalTime operatingStart;
    private final LocalTime operatingEnd;

    public Coach(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
        this.operatingStart = parseStringToTime("08:00");
        this.operatingEnd = parseStringToTime("23:00");
    }

    public DailyRecord attendCrew(String name, LocalDateTime dateTime) {
        validateOperatingTime(dateTime);
        validateHoliday(dateTime);

        return attendanceBook.saveAttendanceRecord(name, dateTime);
    }

    public DailyRecord editCrew(String name, LocalDateTime dateTime) {
        validateOperatingTime(dateTime);
        validateHoliday(dateTime);

        return attendanceBook.editAttendanceRecord(name, dateTime);
    }

    private void validateOperatingTime(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        if(time.isBefore(operatingStart) || time.isAfter(operatingEnd)) {
            throw new IllegalArgumentException(NOT_OPERATING_TIME_ERROR_MESSAGE);
        }
    }

    private void validateHoliday(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        if(Holiday.isHoliday(date)) {
            throw new IllegalArgumentException(HOLIDAY_ERROR_MESSAGE);
        }
    }
}