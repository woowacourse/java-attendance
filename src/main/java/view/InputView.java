package view;

import common.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {
    Scanner sc = new Scanner(System.in);

    public String readFunctionChoice(LocalDate now) {
        System.out.printf("\n오늘은 %s입니다. 가능을 선택해주세요.\n",
                now.format(DateTimeFormat.MONTH_DATE_DAY_FORMATTER)
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

    public String readCrewName() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return LocalTime.parse(sc.nextLine());
    }

    public int readModifyDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(sc.nextLine());
    }

    public LocalTime readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        return LocalTime.parse(sc.nextLine(), DateTimeFormat.HOUR_MINUTE_FORMATTER);
    }
}
