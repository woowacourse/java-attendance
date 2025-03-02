package attendance.view;

import java.time.LocalDate;
import java.util.Scanner;

public class InputView {

    private static final String LINE = System.lineSeparator();
    private static final String TITLE_COMMAND = """
            오늘은 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    public String readCommand(final LocalDate now) {
        System.out.printf(TITLE_COMMAND, TimeFormatter.makeDateMessage(now));
        return readLine();
    }

    public String readNickname() {
        System.out.println(LINE + "닉네임을 입력해 주세요.");
        return readLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return readLine();
    }

    public String readModifyingNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public String readModifyingDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return readLine();
    }

    public String readModifyingTime() {
        System.out.println("언제로 변경하겠습니까?");
        return readLine();
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
