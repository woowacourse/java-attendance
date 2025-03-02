package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputCommand() {
        System.out.println("오늘은 12월 11일 수요일입니다. 기능을 선택해 주세요.");
        printMenu();
        String command = scanner.nextLine();
        System.out.println();
        return command;
    }

    private void printMenu() {
        System.out.println("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료""");
    }

    public String inputAttendNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputAttendingTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }
}
