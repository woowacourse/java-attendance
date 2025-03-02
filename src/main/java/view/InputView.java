package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readFeatureNumber(LocalDate today) {
        System.out.printf("\n오늘은 %s입니다. 기능을 선택해 주세요.\n", formatDate(today));
        System.out.println("1. 출석 확인\n" +
                "2. 출석 수정\n" +
                "3. 크루별 출석 기록 확인\n" +
                "4. 제적 위험자 확인\n" +
                "Q. 종료");
        return scanner.next();
    }

    public String readNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public String readTimeForCheckIn() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.next();
    }

    public String readNickNameForModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public String readDateForModify() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.next();
    }

    public String readTimeForModify() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.next();
    }

    private static String formatDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
        String datePart = date.format(dateFormatter);

        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        return datePart + " " + dayOfWeek;
    }
}
