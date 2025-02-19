package attendance.view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void printOperations(LocalDate today) {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n", today.getMonthValue(), today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }
}
