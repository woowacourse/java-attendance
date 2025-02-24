package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import dto.AttendanceModifyRequestDto;
import dto.AttendanceRequestDto;
import dto.OptionRequestDto;
import util.DateTimeUtil;
import util.RetryHandler;

public class InputView {

    private InputView() {
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static OptionRequestDto scanOption() {
        return RetryHandler.retryUntilSuccessWithReturn(() -> {
            LocalDate now = DateTimeUtil.nowDate();
            System.out.printf("""
                오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료 
                """,
                now.getMonth().getValue(),
                now.getDayOfMonth(),
                now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)
            );
            String option = scanner.nextLine();
            System.out.println();
            return new OptionRequestDto(option);
        });
    }

    public static AttendanceRequestDto scanAttendance() {
        System.out.println("닉네임을 입력해 주세요.");
        String nickname = scanner.nextLine();
        System.out.println("등교 시간을 입력해 주세요.");
        String time = scanner.nextLine();
        System.out.println();
        return AttendanceRequestDto.of(nickname, time);
    }

    public static AttendanceModifyRequestDto scanModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String nickname = scanner.nextLine();
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String day = scanner.nextLine();
        System.out.println("언제로 변경하겠습니까?");
        String time = scanner.nextLine();
        System.out.println();
        return AttendanceModifyRequestDto.of(nickname, day, time);
    }

    public static String scanNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        String nickname = scanner.nextLine();
        System.out.println();
        return nickname;
    }
}
