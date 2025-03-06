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

    public String inputAttendanceCrew() {
        System.out.println("닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public String inputAttendanceDateTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return sc.nextLine();
    }

    public String inputModifyCrew() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public String inputModifyDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return sc.nextLine();
    }

    public String inputModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        return sc.nextLine();
    }

    public String inputCheckCrew() {
        System.out.println("닉네임을 입력해 주세요.");
        return sc.nextLine();
    }
}
