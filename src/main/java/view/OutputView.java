package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OutputView {
    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M월 d일 E요일");

    public void displayMenu(LocalDate today) {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n" +
                "1. 출석 확인%n" +
                "2. 출석 수정%n" +
                "3. 크루별 출석 기록 확인%n" +
                "4. 제적 위험자 확인%n" +
                "Q. 종료%n", DATE_FORMAT.format(today));
    }
}
