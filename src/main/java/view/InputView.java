package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private final String READ_COMMAND_FORMAT = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n1. 출석 확인\n2. 출석 수정\n3. 크루별 출석 기록 확인\n4. 제적 위험자 확인\nQ. 종료\n";

    private final Scanner scanner = new Scanner(System.in);

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readCheckInTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readChangeTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }

    public String readDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readCommand() {
        LocalDate now = LocalDate.now();
        System.out.printf(
                READ_COMMAND_FORMAT,
                now.getMonthValue(),
                now.getDayOfMonth(),
                now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())
        );
        return scanner.nextLine();
    }
}
