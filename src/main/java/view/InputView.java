package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import dto.AttendanceModifyRequest;
import dto.AttendanceRequest;
import util.DayUtil;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String scanOption() {
        LocalDate now = DayUtil.now();
        System.out.println(String.format("""
                오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료 
                """,
            now.getMonth().getValue(),
            now.getDayOfMonth(),
            now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN))
        );
        return scanner.nextLine();
    }

    public static AttendanceRequest scanAttendance() {
        System.out.println("닉네임을 입력해 주세요.");
        String nickname = scanner.nextLine();
        System.out.println("등교 시간을 입력해 주세요.");
        String time = scanner.nextLine();
        return AttendanceRequest.of(nickname, time);
    }

    public static AttendanceModifyRequest scanModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String nickname = scanner.nextLine();
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String day = scanner.nextLine();
        System.out.println("언제로 변경하겠습니까?");
        String time = scanner.nextLine();
        return AttendanceModifyRequest.of(nickname, day, time);
    }
}
