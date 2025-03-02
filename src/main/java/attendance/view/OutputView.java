package attendance.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {
    private final static String METHOD_MESSAGE = """
        1. 출석 확인
        2. 출석 수정
        3. 크루별 출석 기록 확인
        4. 제적 위험자 확인
        Q. 종료""";
    private final static String REQUEST_METHOD = "\n오늘은 MM월 d일 E요일입니다. 기능을 선택해 주세요.";

    public void print(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        print(error);
    }

    public void printRequestMessage(LocalDateTime dateTime) {
        var formatted = DateTimeFormatter.ofPattern(REQUEST_METHOD).format(dateTime);
        print(formatted);
    }

    public void printMethod() {
        print(METHOD_MESSAGE);
    }
}
