package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String inputCommand(String today) {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.\n", today);
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
        return inputValue();
    }

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return inputValue();
    }

    public String getAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return inputValue();
    }

    private String inputValue() {
        return scanner.next().trim();
    }

    public void close() {
        this.scanner.close();
    }
}
