package view;

import constant.InputViewMessage;
import dto.AttendanceCheckInRequest;
import dto.AttendanceHistoryRequest;
import dto.AttendanceOptionRequest;
import dto.AttendanceUpdateRequest;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;
import util.DateTimeGenerator;
import util.InputParser;
import util.InputValidator;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {
    }

    public static AttendanceOptionRequest readAttendanceOptionRequest(DateTimeGenerator dateTimeGenerator) {
        LocalDateTime now = dateTimeGenerator.now();

        println(String.format(InputViewMessage.ATTENDANCE_OPTION_PROMPT.getMessage(),
                now.getMonth().getValue(),
                now.getDayOfMonth(),
                now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        String option = readTrimmedInput();
        InputValidator.validateNullOrBlank(option);

        printNewLine();
        return new AttendanceOptionRequest(option);
    }

    public static AttendanceCheckInRequest readAttendanceCheckInRequest() {
        println(InputViewMessage.ATTENDANCE_CHECK_IN_NICKNAME_PROMPT.getMessage());
        String nickname = readTrimmedInput();
        InputValidator.validateNullOrBlank(nickname);

        println(InputViewMessage.ATTENDANCE_CHECK_IN_TIME_PROMPT.getMessage());
        String time = readTrimmedInput();
        InputValidator.validateNullOrBlank(time);
        InputValidator.validateTime(time);

        printNewLine();
        return new AttendanceCheckInRequest(nickname, time);
    }

    public static AttendanceUpdateRequest readAttendanceUpdateRequest(DateTimeGenerator dateTimeGenerator) {
        println(InputViewMessage.ATTENDANCE_UPDATE_NICKNAME_PROMPT.getMessage());
        String nickname = readTrimmedInput();
        InputValidator.validateNullOrBlank(nickname);

        println(InputViewMessage.ATTENDANCE_UPDATE_DAY_PROMPT.getMessage());
        String day = readTrimmedInput();
        InputValidator.validateNullOrBlank(day);
        InputValidator.validateInteger(day);
        InputValidator.validateDay(day, dateTimeGenerator.now());

        println(InputViewMessage.ATTENDANCE_UPDATE_TIME_PROMPT.getMessage());
        String time = readTrimmedInput();
        InputValidator.validateNullOrBlank(time);
        InputValidator.validateTime(time);

        printNewLine();
        return new AttendanceUpdateRequest(nickname, day, time);
    }

    public static AttendanceHistoryRequest readAttendanceHistoryRequest() {
        println(InputViewMessage.ATTENDANCE_HISTORY_NICKNAME_PROMPT.getMessage());
        String nickname = readTrimmedInput();
        InputValidator.validateNullOrBlank(nickname);

        printNewLine();
        return new AttendanceHistoryRequest(nickname);
    }

    private static String readTrimmedInput() {
        return InputParser.trim(scanner.nextLine());
    }

    private static void println(String message) {
        System.out.println(message);
    }

    private static void printNewLine() {
        System.out.println();
    }
}
