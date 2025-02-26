package view;

import constant.InputViewMessage;
import dto.AttendanceCheckInRequest;
import dto.AttendanceOptionRequest;
import dto.AttendanceUpdateRequest;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {
    }

    public static AttendanceOptionRequest readAttendanceOptionRequest(LocalDateTime dateTime) {
        println(InputViewMessage.ATTENDANCE_OPTION_PROMPT.getMessage(
                dateTime.getMonth().getValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        String option = scanner.nextLine();

        printNewLine();
        return new AttendanceOptionRequest(option);
    }

    public static AttendanceCheckInRequest readAttendanceCheckInRequest() {
        println(InputViewMessage.ATTENDANCE_CHECK_IN_NICKNAME_PROMPT.getMessage());
        String nickname = scanner.nextLine();

        println(InputViewMessage.ATTENDANCE_CHECK_IN_TIME_PROMPT.getMessage());
        String time = scanner.nextLine();

        printNewLine();
        return new AttendanceCheckInRequest(nickname, time);
    }

    public static AttendanceUpdateRequest readAttendanceUpdateRequest(LocalDateTime dateTime) {
        println(InputViewMessage.ATTENDANCE_UPDATE_NICKNAME_PROMPT.getMessage());
        String nickname = scanner.nextLine();

        println(InputViewMessage.ATTENDANCE_UPDATE_DAY_PROMPT.getMessage());
        String day = scanner.nextLine();

        println(InputViewMessage.ATTENDANCE_UPDATE_TIME_PROMPT.getMessage());
        String time = scanner.nextLine();

        printNewLine();
        return new AttendanceUpdateRequest(nickname, day, time);
    }

    private static void println(String message) {
        System.out.println(message);
    }

    private static void printNewLine() {
        System.out.println();
    }
}
