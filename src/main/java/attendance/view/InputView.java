package attendance.view;

import java.util.Scanner;

public class InputView {
    private static final String MENU_OPTION_NOTICE = """
             오늘은 %d월 %d일 %s요일입니다. 기능을 선택해주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료""";

    private final Scanner scanner = new Scanner(System.in);

    public String readMenuCommand(final int month, final int date, final String dayOfWeek) {
        System.out.println(MENU_OPTION_NOTICE.formatted(month, date, dayOfWeek));
        return scanner.nextLine();
    }

    public String readCrewName() {
        System.out.println();
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readModifyCrewName() {
        System.out.println();
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readModifyAttendanceDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readModifyAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
