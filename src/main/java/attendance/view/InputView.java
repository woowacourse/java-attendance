package attendance.view;

import attendance.controller.AttendanceOption;
import attendance.utils.DateConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    public static final String OPTION_MESSAGE = "오늘은 %s입니다. 기능을 선택해 주세요.%n" +
        "1. 출석 확인%n" +
        "2. 출석 수정%n" +
        "3. 크루별 출석 기록 확인%n" +
        "4. 제적 위험자 확인%n" +
        "Q. 종료%n";
    public static final String REMARK_ATTENDANCE_NAME_MESSAGE = "닉네임을 입력해 주세요.";
    public static final String REMARK_ATTENDANCE_TIME_MESSAGE = "등교 시간을 입력해 주세요.";
    public static final String EDIT_ATTENDANCE_NAME_MESSAGE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    public static final String EDIT_ATTENDANCE_DATE_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    public static final String EDIT_ATTENDANCE_TIME_MESSAGE = "언제로 변경하겠습니까?";

    public AttendanceOption readAttendanceOption(LocalDate today) {
        System.out.printf(String.format(OPTION_MESSAGE, DateConverter.convertToString(today)));
        String input = scanner.nextLine();
        return AttendanceOption.find(input);
    }

    public String readRemarkAttendanceName() {
        System.out.println(REMARK_ATTENDANCE_NAME_MESSAGE);
        return scanner.nextLine();
    }

    public LocalTime readRemarkAttendanceTime() {
        System.out.println(REMARK_ATTENDANCE_TIME_MESSAGE);
        String input = scanner.nextLine();
        return parseLocalTime(input);
    }

    public String readEditAttendanceName() {
        System.out.println(EDIT_ATTENDANCE_NAME_MESSAGE);
        return scanner.nextLine();
    }

    public LocalDate readEditAttendanceDate() {
        System.out.println(EDIT_ATTENDANCE_DATE_MESSAGE);
        return parseLocalDate(scanner.nextLine());
    }

    public LocalTime readEditAttendanceTime() {
        System.out.println(EDIT_ATTENDANCE_TIME_MESSAGE);
        return parseLocalTime(scanner.nextLine());
    }

    private LocalTime parseLocalTime(String input) {
        try {
            return LocalTime.parse(input, DateConverter.LOCAL_TIME_FORMAT);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 시간 형식을 입력하셨습니다.");
        }
    }

    private LocalDate parseLocalDate(String input) {
        try {
            int date = Integer.parseInt(input);
            return LocalDate.of(2024, 12, date);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 날짜 형식을 입력하셨습니다.");
        }
    }
}
