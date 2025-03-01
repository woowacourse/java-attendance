package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutPutView {
    public static void displayAttendanceMenu(LocalDate localDate){
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요\n",
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        System.out.println(
                 "1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료\n");
    }

    public static void requestNickName(){
        System.out.println("닉네임을 입력해 주세요.");
    }
}
