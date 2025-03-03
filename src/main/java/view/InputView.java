package view;

import controller.AttendanceCommandController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public final class InputView {

    private InputView() {
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static String readCommand() {
        final LocalDateTime referenceDateTime = AttendanceCommandController.SYSTEM_DATE_TIME;
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("오늘은 MM월 dd일 EEEE", Locale.KOREAN);

        printF("%n%s입니다. 기능을 선택해 주세요.%n", referenceDateTime.format(dateTimeFormatter));
        print("1. 출석 확인");
        print("2. 출석 수정");
        print("3. 크루별 출석 기록 확인");
        print("4. 제적 위험자 확인");
        print("Q. 종료");
        return readInput();
    }

    public static String readNickname() {
        print("닉네임을 입력해 주세요.");
        return readInput();
    }

    public static String readTime() {
        print("등교 시간을 입력해 주세요.");
        return readInput();
    }

    public static String readNicknameForEdit() {
        print("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readInput();
    }

    public static String readEditDate() {
        print("수정하려는 날짜(일)를 입력해 주세요.");
        return readInput();
    }

    public static String readTimeForEdit() {
        print("언제로 변경하겠습니까?");
        return readInput();
    }

    private static String readInput() {
        final String input = scanner.nextLine();

        if (input.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력할 수 없습니다.");
        }
        return input;
    }

    private static void printF(final String message, final Object... args) {
        System.out.printf(message, args);
    }

    private static void print(final String message) {
        System.out.println(message);
    }
}
