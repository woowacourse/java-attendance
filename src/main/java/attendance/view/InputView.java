package attendance.view;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readOption(final LocalDate today) {
        printWelcomeMessage(today);
        return input();
    }

    private static void printWelcomeMessage(final LocalDate today) {
        int month = today.getMonthValue();
        int date = today.getDayOfMonth();
        DayOfWeek day = today.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.\n", month, date, dayName);
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
    }

    public static String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return input();
    }

    public static LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String attendanceTime = input();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(attendanceTime, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("유효한 시간을 입력해주세요.");
        }
    }

    public static String readModifyCrewNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return input();
    }

    public static LocalDate readAttendanceDateToModify(final LocalDate today) {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        int year = today.getYear();
        int month = today.getMonthValue();
        try {
            int date = scanner.nextInt();
            return LocalDate.of(year, month, date);
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("유효한 날짜를 입력해주세요.");
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("유효한 날짜를 입력해주세요.");
        }
    }

    private static String input() {
        return scanner.nextLine();
    }
}
