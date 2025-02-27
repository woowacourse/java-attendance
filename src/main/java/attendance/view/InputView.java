package attendance.view;

import attendance.controller.AttendanceOption;
import attendance.utils.DateConverter;

import java.time.LocalDate;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    public static final String OPTION_MESSAGE = "오늘은 %s입니다. 기능을 선택해 주세요.%n" +
        "1. 출석 확인%n" +
        "2. 출석 수정%n" +
        "3. 크루별 출석 기록 확인%n" +
        "4. 제적 위험자 확인%n" +
        "Q. 종료%n";

    public AttendanceOption readAttendanceOption(LocalDate today) {
        System.out.printf(String.format(OPTION_MESSAGE, DateConverter.convertToString(today)));
        String input = scanner.nextLine();
        return AttendanceOption.find(input);
    }
}
