package view;

import domain.AttendanceBook;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    private static final String MENU_REGEX = "[1234Qq]";
    private static final String TIME_FORMAT = "HH:mm";
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private static final String MENU_NOT_EXISTED = "존재하는 메뉴 번호를 입력해주세요.";
    private static final String NAME_NOT_EXISTED = "존재하지 않는 닉네임입니다.";
    private static final String TIME_FORMAT_NOT_VALID = "올바르지 않은 시간 형식입니다.";
    private static final String RUNNING_TIME_NOT_VALID = "캠퍼스 운영 시간이 아닙니다.";
    private static final String DAY_NOT_VALID = "유효하지 않은 날짜(일) 입니다.";

    public static void validateMenu(String menu) {
        if (!menu.matches(MENU_REGEX)) {
            throw new IllegalArgumentException(MENU_NOT_EXISTED);
        }
    }

    public static void validateName(String name, AttendanceBook attendanceBook) {
        if (!attendanceBook.contains(name)) {
            throw new IllegalArgumentException(NAME_NOT_EXISTED);
        }
    }

    public static void validateTimeFormat(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(TIME_FORMAT_NOT_VALID);
        }
        if (localTime.isBefore(START_TIME) || localTime.isAfter(END_TIME)) {
            throw new IllegalArgumentException(RUNNING_TIME_NOT_VALID);
        }
    }

    public static void validateDate(String dateInput) {
        try {
            int date = Integer.parseInt(dateInput);
            LocalDate.of(2024, 12, date);
        } catch (NumberFormatException | DateTimeException e) {
            throw new IllegalArgumentException(DAY_NOT_VALID);
        }
    }
}
