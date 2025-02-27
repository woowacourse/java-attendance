package view;

import java.util.Scanner;

public class InputView {
    private final Scanner sc;

    public InputView() {
        sc = new Scanner(System.in);
    }

    public String readCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

}
