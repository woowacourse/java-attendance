package attendance.view;

import attendance.util.ErrorMessage;

import java.time.LocalDate;
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
        System.out.println("닉네임을 입력해주세요.");
        return validateInput(scanner.nextLine());
    }

    public static String readAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return validateInput(scanner.nextLine());
    }

    private static String validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_NULL_OR_BLANK_ERROR.getMessage());
        }

        return input;
    }
}
