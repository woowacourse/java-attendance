package view;

import java.util.Scanner;

public class InputView {
    private static final String ASK_NAME = "닉네임을 입력해 주세요.";
    private static final String ASK_TIME = "등교 시간을 입력해 주세요.";

    private static final String ASK_NAME_FOR_MODIFY = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String ASK_DAY_FOR_MODIFY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String ASK_TIME_FOR_MODIFY = "언제로 변경하겠습니까?";

    private final Scanner scanner = new Scanner(System.in);

    // 출석 확인 기능, 크루 출석 기록 확인 기능: 닉네임 입력
    public String askName() {
        System.out.println(ASK_NAME);
        return getUserSelection();
    }

    // 출석 확인 기능: 시간 입력
    public String askTime() {
        System.out.println(ASK_TIME);
        return getUserSelection();
    }

    public String askNameForModify() {
        System.out.println(ASK_NAME_FOR_MODIFY);
        return getUserSelection();
    }

    public String askDayForModify() {
        System.out.println(ASK_DAY_FOR_MODIFY);
        return getUserSelection();
    }

    public String askTimeForModify() {
        System.out.println(ASK_TIME_FOR_MODIFY);
        return getUserSelection();
    }

    public String getUserSelection() {
        String response = scanner.nextLine();
        OutputView.displaySpacing();
        return response;
    }
}