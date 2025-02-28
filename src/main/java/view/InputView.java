package view;

import static util.Constants.*;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readSelectedMenu() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN);
        String formattedDate = TODAY.format(dateFormatter);
        String formattedDayOfWeek = TODAY.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.println("오늘은 " + formattedDate + " " + formattedDayOfWeek + "입니다. 기능을 선택해 주세요.\n"
                + "1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");

        String input = scanner.nextLine();
        InputValidator.validateSelectedMenu(input);
        return input;
    }

    public String readName() {
        System.out.println("닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        InputValidator.validateName(input);
        return input;
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine();
        InputValidator.validateTime(input);
        return input;
    }

    public String readModifyDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = scanner.nextLine();
        InputValidator.validateDay(input);
        return input;
    }

    public String readModifyName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        InputValidator.validateName(input);
        return input;
    }

    public String readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine();
        InputValidator.validateTime(input);
        return input;
    }
}
