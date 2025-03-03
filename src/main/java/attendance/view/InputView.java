package attendance.view;

import attendance.util.DateFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class InputView {
    private static final String TODAY_IS = "\n오늘은 %s입니다. ";
    private static final String ENTER_OPTION = """
            기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;
    private static final String ENTER_NICKNAME = "\n닉네임을 입력해 주세요.\n";
    private static final String ENTER_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.\n";
    private static final String ENTER_NICKNAME_FOR_MODIFY = "\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.\n";
    private static final String ENTER_DAY_FOR_MODIFY = "수정하려는 날짜(일)를 입력해 주세요.\n";
    private static final String ENTER_TIME_FOR_MODIFY = "언제로 변경하겠습니까?\n";

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readOption(final LocalDateTime today) {
        System.out.printf(TODAY_IS, DateFormatter.formatDate(today));
        System.out.print(ENTER_OPTION);
        return readLine();
    }

    public String readNickname() {
        System.out.print(ENTER_NICKNAME);
        return readLine();
    }

    public LocalTime readAttendanceTime() {
        System.out.print(ENTER_ATTENDANCE_TIME);
        return LocalTime.parse(readLine());
    }

    public String readNicknameForModify() {
        System.out.print(ENTER_NICKNAME_FOR_MODIFY);
        return readLine();
    }

    public int readDayForModify() {
        System.out.print(ENTER_DAY_FOR_MODIFY);
        return Integer.parseInt(readLine());
    }

    public LocalTime readTimeForModify() {
        System.out.print(ENTER_TIME_FOR_MODIFY);
        return LocalTime.parse(readLine());
    }

    private String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
