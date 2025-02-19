package attendance.view;

import java.util.Scanner;

public class InputView {
    private static Scanner scanner = new Scanner(System.in);

    private InputView() {}

    public static String readNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public static String readCheckInTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }
}
