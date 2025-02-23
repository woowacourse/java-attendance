package attendance.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputView {
    private static final String TODAY_INFO = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.";
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputOption(LocalDate localDate) {
        System.out.println(TODAY_INFO.formatted(localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getDayOfWeek()));
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
        return userInput();
    }

    public static String inputCrewName() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return userInput();
    }

    public static LocalTime inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String userInput = userInput();
        try {
            return parseStringToLocalTime(userInput);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다.");
        }
    }

    public static LocalDate inputModifyDate(LocalDate today) {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        try {
            int modifyDate = scanner.nextInt();
            int year = today.getYear();
            int month = today.getMonthValue();
            return LocalDate.of(year, month, modifyDate);
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("숫자가 아닙니다.");
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("잘못된 날짜입니다.");
        }
    }

    private static LocalTime parseStringToLocalTime(String userInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(userInput, formatter);
    }

    private static String userInput() {
        String input = scanner.nextLine();
        System.out.println("hhh" + input);
        return input;
    }
}
