package attendance.view;

import java.util.Scanner;

public class InputView {
    private static final String MENU_STRING = """
            오늘은 %s월 %s일 %s요일입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    private final Scanner scanner = new Scanner(System.in);

    public String readCommand(final String month, final String date, final String day) {
        System.out.println(MENU_STRING.formatted(month, date, day));
        return scanner.nextLine();
    }

    public String readCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readModifyDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
