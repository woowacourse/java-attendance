package view;

import static view.InputValidator.validateInteger;
import static view.InputValidator.validateTime;

import java.util.Scanner;

public class InputView {

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String insertTime() {
        System.out.println("등교 시간을 입력해 주세요.\n");
        String rawTime = readLine();
        validateTime(rawTime);
        return rawTime;
    }

    public String insertChangeName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public int insertChangeDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String rawDayOfMonth = readLine();
        validateTime(rawDayOfMonth);
        return Integer.parseInt(rawDayOfMonth);
    }

    public String insertChangeTime() {
        System.out.println("언제로 변경하겠습니까?");
        String rawTime = readLine();
        validateInteger(rawTime);
        return rawTime;
    }

    public String insertName() {
        System.out.println("닉네임을 입력해 주세요.");
        return readLine();
    }
}
