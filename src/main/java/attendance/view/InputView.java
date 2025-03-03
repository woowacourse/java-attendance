package attendance.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputView {
    private static final String ENTER_NICKNAME = "\n닉네임을 입력해 주세요.\n";

    private static final String ENTER_OPTION = """
            기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readOption() {
        System.out.print(ENTER_OPTION);
        return readLine();
    }

    public String readNickname() {
        System.out.print(ENTER_NICKNAME);
        return readLine();
    }

    private String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
