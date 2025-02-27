package attendance.view;

import static attendance.error.ErrorMessage.INVALID_DATE;
import static attendance.error.ErrorMessage.INVALID_INPUT;
import static attendance.error.ErrorMessage.INVALID_TIME_FORMAT;
import static attendance.error.ErrorMessage.NOT_NUMBER;

import attendance.domain.DayOfWeek;
import attendance.error.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {

    private static final String NUMBER_REGAX = "\\d+";
    private static final String DATE_FORMAT = "HH:mm";
    private static final String TODAY_INFO = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.";
    private static final String INPUT_NICKNAME_MESSAGE = "닉네임을 입력해 주세요.";
    private static final String INPUT_MODIFY_DATE_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String INPUT_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private static final String INPUT_OPTION_MESSAGE = "1. 출석 확인\n"
        + "2. 출석 수정\n"
        + "3. 크루별 출석 기록 확인\n"
        + "4. 제적 위험자 확인\n"
        + "Q. 종료";

    private final Scanner scanner = new Scanner(System.in);

    public String inputOption(LocalDate currentDate) {
        DayOfWeek currentDayOfWeek = DayOfWeek.calculateDayOfWeek(currentDate);
        System.out.println(
            TODAY_INFO.formatted(currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                currentDayOfWeek.getName()));
        System.out.println(INPUT_OPTION_MESSAGE);
        String input = userInput();
        validateInputOption(input);
        return input;
    }

    public String inputCrewName() {
        System.out.println(INPUT_NICKNAME_MESSAGE);
        return userInput();
    }

    public LocalTime inputAttendanceTime() {
        System.out.println(INPUT_ATTENDANCE_TIME);
        String userInput = userInput();
        try {
            return parseStringToLocalTime(userInput);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT.getMessage());
        }
    }

    public LocalDate inputModifyDate(LocalDate currentDate) {
        System.out.println(INPUT_MODIFY_DATE_MESSAGE);
        int endDate = currentDate.lengthOfMonth();
        String userInput = userInput();
        validateIsNumber(userInput);
        int date = Integer.parseInt(userInput);
        validateInvalidDate(date < 1 || date > endDate, INVALID_DATE);
        return LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), date);
    }

    private void validateInvalidDate(boolean date, ErrorMessage invalidDate) {
        if (date) {
            throw new IllegalArgumentException(invalidDate.getMessage());
        }
    }

    private void validateIsNumber(String userInput) {
        validateInvalidDate(!userInput.matches(NUMBER_REGAX), NOT_NUMBER);
    }

    private static LocalTime parseStringToLocalTime(String userInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalTime.parse(userInput, formatter);
    }

    private String userInput() {
        return scanner.nextLine();
    }

    private void validateInputOption(String userInput) {
        if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3")
            || userInput.equals("4") ||
            userInput.equals("Q")) {
            return;
        }
        throw new IllegalArgumentException(INVALID_INPUT.getMessage());
    }
}
