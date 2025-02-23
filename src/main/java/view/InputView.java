package view;

import util.Parser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {
    private static final String ASK_NAME = "닉네임을 입력해 주세요.";
    private static final String ASK_TIME = "등교 시간을 입력해 주세요.";

    private static final String ASK_NAME_FOR_MODIFY = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String ASK_DAY_FOR_MODIFY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String ASK_TIME_FOR_MODIFY = "언제로 변경하겠습니까?";

    private final Scanner scanner = new Scanner(System.in);

    public String askName() {
        System.out.println(ASK_NAME);
        return getUserSelection();
    }

    public String askTime() {
        System.out.println(ASK_TIME);
        return getUserSelection();
    }

    public String askNameForModify() {
        System.out.println(ASK_NAME_FOR_MODIFY);
        return getUserSelection();
    }

    public LocalDate askDayForModify() {
        System.out.println(ASK_DAY_FOR_MODIFY);
        return Parser.parseInputDay(getUserSelection());
    }

    public LocalTime askTimeForModify() {
        System.out.println(ASK_TIME_FOR_MODIFY);
        return Parser.parseInputTime(getUserSelection());
    }

    public String getUserSelection() {
        String response = scanner.nextLine();
        OutputView.displaySpacing();
        return response;
    }
}