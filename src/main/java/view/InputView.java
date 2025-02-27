package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String readMenuOption(LocalDate today) {
        System.out.printf("오늘은 12월 %d일 %s입니다. 기능을 선택해 주세요.\n",
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(
                        TextStyle.FULL, Locale.KOREAN));
        System.out.print("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
        return readInput();
    }

    public String readCheckAttendanceNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readInput();
    }

    public String readCheckAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = readInput();
        inputValidator.validateTimeFormat(input);
        return input;
    }

    public String readChangeAttendanceNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readInput();
    }

    public String readChangeAttendanceDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = readInput();
        inputValidator.validateDateFormat(input);
        return input;
    }

    public String readChangeAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = readInput();
        inputValidator.validateTimeFormat(input);
        return input;
    }

    public String readShowCrewAttendanceNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readInput();
    }

    private String readInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().trim();
    }
}
