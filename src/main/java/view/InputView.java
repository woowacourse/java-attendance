package view;

import domain.Command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import static controller.AttendanceController.TODAY;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public Command readCommand() {
        LocalDateTime today = TODAY;
        System.out.printf("오늘은 %d월 %s일 %s입니다. 기능을 선택해 주세요.\n" +
                        "1. 출석 확인\n" +
                        "2. 출석 수정\n" +
                        "3. 크루별 출석 기록 확인\n" +
                        "4. 제적 위험자 확인\n" +
                        "Q. 종료\n%n", today.getMonthValue(), today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );
        return Command.getCommand(scanner.nextLine());
    }

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        LocalDateTime nowTime = TODAY;
        String year = String.valueOf(nowTime.getYear());
        String month = String.format("%02d", nowTime.getMonthValue());
        String date = String.valueOf(nowTime.getDayOfMonth());

        return year + "-" + month + "-" + date + " " + scanner.nextLine();
    }

    public String readNickNameForChange() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int readDateForChange() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(scanner.nextLine());
    }

    public String readTimeForChange() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }

}
