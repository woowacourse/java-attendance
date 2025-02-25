package presentation.view;

import java.time.LocalDate;
import java.util.Scanner;
import util.DateTimeUtil;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputCommand() {
        StringBuilder commandDiscription = new StringBuilder();
        commandDiscription
                .append("오늘은 ")
                .append(DateTimeUtil.convertLocalDateToString(LocalDate.now()))
                .append("입니다. 기능을 선택해주세요.\n")
                .append("1. 출석 확인\n")
                .append("2. 출석 수정\n")
                .append("3. 크루별 출석 기록 확인\n")
                .append("4. 제적 위험자 확인\n")
                .append("Q. 종료\n");
        System.out.print(commandDiscription.toString());

        return getInput();
    }

    public static String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return getInput();
    }

    public static String inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return getInput();
    }

    public static String inputAttendanceDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요");
        return getInput();
    }

    private static String getInput() {
        return scanner.next().trim();
    }
}
