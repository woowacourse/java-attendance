package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public String readTimeForCheckIn() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.next();
    }

    public String readNickNameForModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public String readDateForModify() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.next();
    }

    public String readTimeForModify() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.next();
    }

}
