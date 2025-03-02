package view;

import static util.Constants.ERROR_HEADER;
import static util.Constants.TIME_FORMAT;
import static util.Constants.TODAY;

import domain.AttendanceBook;
import domain.AttendancePolicy;
import domain.CrewName;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    private static final String TIME_FORMAT_ERROR = "시간 형식(HH:mm)이 올바르지 않습니다.";
    private static final String NAME_FORMAT_ERROR = "닉네임은 2자 이상 4자 이하로 입력해주세요.";
    private static final String INVALID_DATE_ERROR = "유효하지 않은 날짜입니다.";
    private static final String INVALID_MENU_ERROR = "유효하지 않은 메뉴입니다.";
    private static final String HOLIDAY_ERROR = "주말 및 공휴일은 출석할 수 없습니다.";
    private static final String NOT_RUNNING_TIME_ERROR = "캠퍼스 운영시간이 아닙니다.";
    private static final String NAME_NOT_EXISTED_ERROR = "존재하지 않는 닉네임입니다.";

    private static final String MENU_PATTERN = "[1234Qq]";
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 4;

    public static void validateTime(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try {
            LocalTime parseTime = LocalTime.parse(time, dateTimeFormatter);
            checkRunningTime(parseTime);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_HEADER + TIME_FORMAT_ERROR);
        }
    }

    public static void validateName(String name, AttendanceBook attendanceBook) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(ERROR_HEADER + NAME_FORMAT_ERROR);
        }
        if (attendanceBook.isNotExistedName(new CrewName(name))) {
            throw new IllegalArgumentException(ERROR_HEADER + NAME_NOT_EXISTED_ERROR);
        }
    }

    public static void validateDay(String day) {
        try {
            int dayNumber = Integer.parseInt(day);
            LocalDate date = LocalDate.of(TODAY.getYear(), TODAY.getMonth(), dayNumber);
            checkRunningDay(date);
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_HEADER + INVALID_DATE_ERROR);
        }
    }

    public static void validateSelectedMenu(String selectedMenu) {
        if (!selectedMenu.matches(MENU_PATTERN)) {
            throw new IllegalArgumentException(ERROR_HEADER + INVALID_MENU_ERROR);
        }
    }

    private static void checkRunningTime(LocalTime time) {
        if (AttendancePolicy.isInvalidTime(time)) {
            throw new IllegalArgumentException(ERROR_HEADER + NOT_RUNNING_TIME_ERROR);
        }
    }

    private static void checkRunningDay(LocalDate date) {
        if (date.isAfter(TODAY)) {
            throw new IllegalArgumentException(ERROR_HEADER + INVALID_DATE_ERROR);
        }
        if (AttendancePolicy.isHoliday(date)) {
            throw new IllegalArgumentException(ERROR_HEADER + HOLIDAY_ERROR);
        }
    }
}
