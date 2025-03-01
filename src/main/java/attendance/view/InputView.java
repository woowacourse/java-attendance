package attendance.view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import attendance.controller.Command;
import attendance.dto.AttendanceHistoryRequest;
import attendance.dto.AttendanceRequest;
import attendance.dto.ModifyAttendanceRequest;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {
    }

    public static Command menu(LocalDate today) {
        System.out.printf("""
                오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """,
            today.getMonthValue(),
            today.getDayOfMonth(),
            today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
        return Command.from(scanner.nextLine());
    }

    public static AttendanceRequest attendance() {
        System.out.println("닉네임을 입력해 주세요.");
        String name = scanner.nextLine();
        System.out.println("등교 시간을 입력해 주세요.");
        String time = scanner.nextLine();
        return AttendanceRequest.of(name, time);
    }

    public static ModifyAttendanceRequest modifyAttendance(LocalDate date) {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String name = scanner.nextLine();
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String day = scanner.nextLine();
        System.out.println("언제로 변경하겠습니까?");
        String time = scanner.nextLine();
        return ModifyAttendanceRequest.of(name, day, time, date);
    }

    public static AttendanceHistoryRequest attendanceHisotory() {
        System.out.println("닉네임을 입력해 주세요.");
        return new AttendanceHistoryRequest(scanner.nextLine());
    }
}
