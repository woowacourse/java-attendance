package attendance.view;

import java.time.LocalDate;
import java.util.Scanner;

public class InputView {
    private static final String TODAY_INFO = "오늘은 %d월 %s일 %s입니다. 기능을 선택해 주세요.";

    private final Scanner sc = new Scanner(System.in);

    public String inputOption(LocalDate localDate) {
        System.out.println(TODAY_INFO.formatted(localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getDayOfWeek()));
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
        return userInput();
    }

    private String userInput() {
        return sc.nextLine();
    }
}
