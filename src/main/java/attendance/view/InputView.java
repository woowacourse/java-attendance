package attendance.view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    Scanner sc = new Scanner(System.in);

    public String displayMainMenu() {
        int nowDay = LocalDateTime.now().getDayOfMonth();
        String nowDayOfWeek = LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String mainMenu = """
            오늘은 12월 %d일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;
        System.out.printf(mainMenu,nowDay,nowDayOfWeek);
        return sc.nextLine();
    }

    public String inputCrew() {
        System.out.println("닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public String inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return sc.nextLine();
    }
}
