package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView() {
        scanner = new Scanner(System.in);
    }

    public String readLine(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public String enterMenuItem() {
        return readLine("1. 출석 확인\n2. 출석 수정\n3. 크루별 출석 기록 확인\n4. 제적 위험자 확인\nQ. 종료");
    }

    public String enterNickname() {
        return readLine("닉네임을 입력해 주세요.");
    }

    public String enterAttendanceTime() {
        return readLine("등교 시간을 입력해 주세요.");
    }

    public String enterNicknameForEdit() {
        return readLine("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public String enterAttendanceDateForEdit() {
        return readLine("수정하려는 날짜(일)을 입력해 주세요.");
    }

    public String enterAttendanceTimeForEdit() {
        return readLine("언제로 변경하겠습니까?");
    }
}
