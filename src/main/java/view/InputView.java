package view;

import controller.Menu;

import java.util.Arrays;
import java.util.List;
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

    public List<Integer> readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        //TODO : LocalTime으로 파싱하기
        return Arrays.stream(scanner.nextLine().split(":")).map(Integer::parseInt).toList();
    }
}
