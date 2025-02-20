package view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String insertNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return getInput();
    }

    public String insertChangeDateNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return getInput();
    }

    public String insertChangeTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = getInput();
        inputValidator.validateTimeFormat(input);
        return input;
    }

    public String insertTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = getInput();
        inputValidator.validateTimeFormat(input);
        return input;
    }

    public int insertChangeDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = getInput();
        inputValidator.validateInteger(input);
        return Integer.parseInt(input);
    }

    public String insertFunction(LocalDateTime today) {
        String dayOfWeekKorean = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.println(
                String.format("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.", today.getMonth().getValue(), today.getDayOfMonth(),
                        dayOfWeekKorean));
        System.out.print("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
        return getInput();
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
}
