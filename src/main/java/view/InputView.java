package view;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import util.DateTimeUtil;
import util.ExceptionHandler;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter MENU_DATE_TIME_FORMAT
            = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);

    public static String scanMenuCommand() {
        System.out.printf("""
                오늘은 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료       
                """, DateTimeUtil.nowDate().format(MENU_DATE_TIME_FORMAT));
        return SCANNER.nextLine();
    }

    public static String scanNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return SCANNER.nextLine();
    }

    public static String scanAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return SCANNER.nextLine();
    }

    public static String scanNicknameToModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return SCANNER.nextLine();
    }

    public static int scanDayToModify() {
        return ExceptionHandler.retryUntilSuccessWithReturn(() -> {
            System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
            String day = SCANNER.nextLine();
            validateInteger(day);
            return Integer.parseInt(day);
        });
    }

    public static String scanTimeToModify() {
        System.out.println("언제로 변경하겠습니까?");
        return SCANNER.nextLine();
    }

    private static void validateInteger(String day) {
        try {
            Integer.parseInt(day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(day + ": 정수가 아닙니다.");
        }
    }
}
