package view;

import domain.Day;

import java.time.LocalDate;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readMenu(LocalDate date) {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n" +
                "1. 출석 확인%n" +
                "2. 출석 수정%n" +
                "3. 크루별 출석 기록 확인%n" +
                "4. 제적 위험자 확인%n" +
                "Q. 종료%n", date.getMonthValue(), date.getDayOfMonth(), Day.getDay(date).getName());
        String input = scanner.nextLine();
        validateMenuInput(input);
        return input;
    }

    private void validateMenuInput(String input) {
        if (!input.matches("[1234Qq]")) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 메뉴입니다.");
        }
    }
}
