package attendance.view;

import attendance.util.Console;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class InputView {

    public String inputFunction() {

        final int month = LocalDate.now().getMonthValue();
        final int date = LocalDate.now().getDayOfMonth();
        final String day = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n", month, date, day);

        System.out.println("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                """);

        return Console.read();
    }

    public String inputCrewName() {

        System.out.println("닉네임을 입력해 주세요.");
        return Console.read();
    }

    public String inputAttendTime() {

        System.out.println("등교 시간을 입력해 주세요.");
        return Console.read();
    }

    public String inputModifyCrewName() {

        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return Console.read();
    }

    public int inputModifyDay() {

        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Console.readInt();
    }

    public String inputModifyTime() {

        System.out.println("언제로 변경하겠습니까?");
        return Console.read();
    }
}
