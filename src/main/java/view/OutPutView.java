package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceStatus;

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

    public static void requestModifyNickName(){
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public static void requestModifyDate(){
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public static void askForNewAttendanceTime(){
        System.out.println("언제로 변경하겠습니까?");
    }

    public static void displayModifyAttendanceRecord(String beforeRecord, String modifyRecord){
        System.out.println(beforeRecord + "->" + modifyRecord + "수정 완료!");
    }

    public static void displayAttendanceRecord(String record){
        System.out.println(record);
    }
}
