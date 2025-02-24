package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    Scanner sc = new Scanner(System.in);
    TextStyle textStyle = TextStyle.FULL;
    Locale locale = Locale.of("ko", "KR");
    LocalDate nowDate = LocalDate.of(2024, 12, 13);

    public String readFunctionChoice() {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 가능을 선택해주세요.\n",
                nowDate.getMonthValue(),
                nowDate.getDayOfMonth(),
                nowDate.getDayOfWeek().getDisplayName(textStyle, locale)
        );
        System.out.print("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
        return sc.nextLine();
    }
}
