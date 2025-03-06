package attendance.view;

import attendance.util.StringParser;
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
    private static final String TITLE_NICKNAME = "닉네임을 입력해 주세요.";
    private static final String TITLE_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private static final String TITLE_MODIFYING_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String TITLE_MODIFYING_DAY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String TITLE_ASK_TIME = "언제로 변경하겠습니까?";

    public String readCommand(final LocalDate now) {
        System.out.printf(TITLE_COMMAND, TimeFormatter.makeDateMessage(now));
        return readLine();
    }

    public String readNickname() {
        System.out.println(LINE + TITLE_NICKNAME);
        return readLine();
    }

    public String readAttendanceTime() {
        System.out.println(TITLE_ATTENDANCE_TIME);
        return readLine();
    }

    public String readModifyingNickname() {
        System.out.println(LINE + TITLE_MODIFYING_NICKNAME);
        return readLine();
    }

    public LocalDate readModifyingDay(final LocalDate now) {
        System.out.println(TITLE_MODIFYING_DAY);
        String input = readLine();
        return StringParser.parseLocalDate(input, now);
    }

    public String readModifyingTime() {
        System.out.println(TITLE_ASK_TIME);
        return readLine();
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
