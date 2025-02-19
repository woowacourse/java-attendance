package view;

import java.util.Scanner;

public class InputView {
    //TODO : 형식 검사
    public String insertNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return getInput();
    }

    public String insertChangeDateNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return getInput();
    }

    public String insertChangeTime() {
        System.out.println("언제로 변경하겠습니까?");
        return getInput();
    }

    public String insertTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return getInput();
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int insertChangeDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(getInput());
    }
}
