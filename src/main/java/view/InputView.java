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

    public String inputMenu(LocalDate today) {
        String koreanDayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.%n", today.getMonth().getValue(), today.getDayOfMonth(),
                koreanDayOfWeek);
        System.out.print("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);

        return scanner.nextLine();
    }

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public String inputTime() {
        System.out.println("등교 시간을 입력해 주세요.");

        return scanner.nextLine();
    }

    public String inputUpdateNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public String inputUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");

        return scanner.nextLine();
    }

    public String inputUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");

        return scanner.nextLine();
    }
}
