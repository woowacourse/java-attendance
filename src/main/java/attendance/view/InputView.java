package attendance.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String inputCommand() {
        System.out.printf("기능을 선택해 주세요.%n");
        System.out.println("1. 출석 확인\n2. 출석 수정\n3. 크루별 출석 기록 확인\n4. 제적 위험자 확인\nQ. 종료");
        return trim(readLine());
    }

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return trim(readLine());
    }

    public String inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return trim(readLine());
    }

    public String inputNicknameForUpdateAttendance() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return trim(readLine());
    }

    public int inputDateForUpdateAttendance() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return parseInt(trim(readLine()));
    }

    public String inputTimeForUpdateAttendance() {
        System.out.println("언제로 변경하시겠습니끼?");
        return trim(readLine());
    }

    private String trim(String input) {
        return input.replaceAll(" ", "");
    }

    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다.");
        }
    }

    private String readLine() {
        try {
            return scanner.nextLine();
        } catch (RuntimeException e) {
            throw new IllegalStateException("입력을 받는 중 문제가 발생했습니다.");
        }
    }
}
