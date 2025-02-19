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
}
