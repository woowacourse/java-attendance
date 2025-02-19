package attendance.view;

import java.util.Scanner;

public class InputView {
    Scanner scanner = new Scanner(System.in);

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int inputUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(scanner.nextLine());
    }

    public String inputUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
