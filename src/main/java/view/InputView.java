package view;


import static util.Constants.FIXED_MONTH;

import domain.AttendanceDateTime;
import domain.Command;
import util.DayOfWeekKorean;
import error.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import java.util.Scanner;

public final class InputView {

    private static Scanner scanner = new Scanner(System.in);

    private InputView() {
    }

    public static String readCommand(final AttendanceDateTime attendanceDateTime) {
        System.out.println(
                getMessageFormat(attendanceDateTime));
        for (Command command : Command.values()) {
            System.out.println(String.format("%s. %s", command.getCommandNumber(), command.getCommandName()));

        }
        final String input = scanner.nextLine();
        validateInput(input);

        return input;
    }

    public static String readNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        validateInput(input);
        return input;
    }

    public static String readUpdateNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        validateInput(input);
        return input;
    }

    public static String readDateTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine();
        validateInput(input);
        return input;
    }

    public static String readUpdateDateTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine();
        validateInput(input);
        return input;
    }

    public static String readUpdateDate() {
        System.out.println("수정하려는 날짜(일)을 입력해 주세요.");
        String input = scanner.nextLine();
        validateInput(input);
        return input;
    }

    private static void validateInput(final String input) {
        if (input.isBlank()) {
            throw new CustomIllegalArgumentException("값을 입력해주세요.");
        }
    }

    private static String getMessageFormat(AttendanceDateTime attendanceDateTime) {
        return String.format("오늘은 %s월 %s일 %s입니다. 기능을 선택해주세요.", FIXED_MONTH, attendanceDateTime.getDayOfMonth(),
                DayOfWeekKorean.getKoreanName(attendanceDateTime.getDayOfWeek()));
    }
}
