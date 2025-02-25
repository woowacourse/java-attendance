package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String scanNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return SCANNER.nextLine();
    }
}
