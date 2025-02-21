package view;

import controller.facade.Menu;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public Menu readMenu() {
        return Menu.of(scanner.nextLine());
    }

    public int readModifyDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(scanner.nextLine());
    }

    public LocalTime readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(input, formatter);
    }
}
