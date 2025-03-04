package attendance.view;

import attendance.util.ErrorMessage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {}

    public static String readFunction(LocalDate today) {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n" +
                "1. 출석 확인%n" +
                "2. 출석 수정%n" +
                "3. 크루별 출석 기록 확인%n" +
                "4. 제적 위험자 확인%n" +
                "Q. 종료%n",
                today.getMonthValue(), today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
        return validateInput(scanner.nextLine());
    }

    public static String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return validateInput(scanner.nextLine());
    }

    public static LocalTime readAttendTimeForRecord() {
        System.out.println("등교 시간을 입력해 주세요.");
        return parseTime(validateInput(scanner.nextLine()));
    }

    public static String readNicknameForEdit() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return validateInput(scanner.nextLine());
    }

    public static int readAttendDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return parseInt(validateInput(scanner.nextLine()));
    }

    public static LocalTime readAttendTimeForEdit() {
        System.out.println("언제로 변경하겠습니까?");
        return parseTime(validateInput(scanner.nextLine()));
    }

    private static String validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_NULL_OR_BLANK_ERROR.getMessage());
        }

        return input;
    }

    private static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT_ERROR.getMessage());
        }
    }

    private static LocalTime parseTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TIME_FORMAT_ERROR.getMessage());
        }
    }
}
