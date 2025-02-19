package view;

import java.util.Scanner;

public class InputView {
    public String insertNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return getInput();
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
