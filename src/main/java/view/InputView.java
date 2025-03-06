package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readOptionNumber(LocalDate nowDate) {
        String dayOfWeek = nowDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%n오늘은 %d월 %02d일 %s입니다. 기능을 선택해 주세요.%n", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                dayOfWeek);
        System.out.printf("1. 출석 확인%n"
                + "2. 출석 수정%n"
                + "3. 크루별 출석 기록 확인%n"
                + "4. 제적 위험자 확인%n"
                + "Q. 종료%n");
        return readLine();
    }

    public String readName() {
        System.out.printf("%n닉네임을 입력해 주세요.%n");
        return readLine();
    }

    public String readAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return readLine();
    }

    public String readEditName() {
        System.out.printf("%n출석을 수정하려는 크루의 닉네임을 입력해 주세요.%n");
        return readLine();
    }

    public String readEditDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return readLine();
    }

    public String readEditTime() {
        System.out.println("언제로 변경하겠습니까?");
        return readLine();
    }

    private String readLine() {
        String input = scanner.nextLine();
        validateInput(input);
        return input;
    }

    private void validateInput(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 입력값이 비어있습니다.");
        }
    }
}
