package attendance.view;

import java.util.Scanner;

public class Input {
    private static final String MENU_STRING = """
            오늘은 %s월 %s일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    private final Scanner scanner = new Scanner(System.in);

    public String readCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readPresentTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readCommand(String month, String date, String day) {
        System.out.println(MENU_STRING.formatted(month, date, day));
        return scanner.nextLine();
    }
}
