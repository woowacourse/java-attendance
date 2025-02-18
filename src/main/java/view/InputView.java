package view;

import java.time.LocalDateTime;
import java.util.Scanner;

public class InputView {
    Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String readName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }
}
