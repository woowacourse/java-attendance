package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readNickNameForCheckIn() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public String readTimeForCheckIn() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.next();
    }
}
