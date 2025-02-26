package view;

import constant.InputViewMessage;
import dto.AttendanceOptionRequest;
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

    private static void println(String message) {
        System.out.println(message);
    }

    private static void printNewLine() {
        System.out.println();
    }
}
