package view;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import util.DateTimeUtil;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter MAIN_MENU_FORMAT
            = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);

    public static String scanMainMenuOption() {
        System.out.printf("""
                오늘은 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료       
                """, DateTimeUtil.now().format(MAIN_MENU_FORMAT));
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
}
