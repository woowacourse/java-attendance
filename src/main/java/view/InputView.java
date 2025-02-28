package view;

import static util.Constants.*;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public void readSelectedMenu() {
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
//        InputValidator.
    }


}
