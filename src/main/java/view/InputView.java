package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private static final String READ_COMMAND_FORMAT = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n"
            + "1. 출석 확인%n"
            + "2. 출석 수정%n"
            + "3. 크루별 출석 기록 확인%n"
            + "4. 제적 위험자 확인%n"
            + "Q. 종료%n";

    private final Scanner scanner = new Scanner(System.in);

    public String readCommand(LocalDate today) {
        System.out.printf(
                READ_COMMAND_FORMAT,
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())
        );
        return scanner.nextLine();
    }

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readCheckInTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }
}
