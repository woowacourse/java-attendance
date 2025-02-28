package view;

import java.time.LocalDate;
import java.util.Scanner;

public class Input {
    private final Scanner scanner;

    public Input(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getMenuInput(LocalDate today) {
        System.out.printf("""
                오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """,
                today.getMonth().getValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek());
        return scanner.nextLine();
    }

    public String getNameInput() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String getTimeInput() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }
}
