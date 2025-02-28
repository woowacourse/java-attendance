package view;

import domain.AttendanceController;
import domain.Command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String getTodayLocalDateTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String time = scanner.nextLine();
        return AttendanceController.TODAY_LOCAL_DATE +" "+ time;
    }

    public Command getCommand() {
        LocalDate today = LocalDate.parse(AttendanceController.TODAY_LOCAL_DATE, AttendanceController.TODAY_FORMATTER);
        System.out.printf("오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.\n", today.getMonthValue(), today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
        System.out.println("1. 출석 확인\n" +
                "2. 출석 수정\n" +
                "3. 크루별 출석 기록 확인\n" +
                "4. 제적 위험자 확인\n" +
                "Q. 종료");
        return Command.findCommand(scanner.nextLine());
    }

    public String getChangeableNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int getChangeableDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(scanner.nextLine());
    }

    public LocalTime getChangeableTime() {
        System.out.println("언제로 변경하겠습니까?");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String timeInput = scanner.nextLine();
        return LocalTime.parse(timeInput, dateTimeFormatter);
    }
}
