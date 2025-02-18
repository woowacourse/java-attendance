package view;

import java.util.Scanner;

public class InputView {
    private final String INPUT_NAME_MESSAGE = "닉네임을 입력해 주세요.";
    private final String INPUT_TIME_MESSAGE = "등교 시간을 입력해 주세요.";

    public String readName(){
        Scanner sc = new Scanner(System.in);
        System.out.println(INPUT_NAME_MESSAGE);
        return sc.nextLine();
    }

    public String readTime(){
        Scanner sc = new Scanner(System.in);
        System.out.println(INPUT_TIME_MESSAGE);
        return sc.nextLine();
    }
}
