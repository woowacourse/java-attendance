package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String inputCommand(LocalDate today) {
        System.out.printf("오늘은 %s 입니다. 기능을 선택해 주세요.%n", today.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")));
        System.out.println("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료""");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        System.out.println();
        return input;
    }

    public String inputNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    public String inputEditNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    public String inputDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    public String inputTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    public String inputEditTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    private void validateEmptyInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("공백은 입력할 수 없습니다.");
        }
    }

}
