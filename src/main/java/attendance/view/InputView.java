package attendance.view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {

    private static final String NICKNAME_INPUT_MESSAGE = "닉네임을 입력해 주세요.";
    private static final String ATTENDANCE_TIME_INPUT_MESSAGE = "등교시간을 입력해주세요.";

    private final Scanner scanner = new Scanner(System.in);

    private InputView() {}

    public static InputView create() {
        return new InputView();
    }

    public String inputNickname() {
        printMessage(NICKNAME_INPUT_MESSAGE);
        return userInput();
    }

    public LocalTime inputAttendanceTime() {
        printMessage(ATTENDANCE_TIME_INPUT_MESSAGE);
        String userInput = userInput();
        LocalTime attendanceTime;
        try {
            attendanceTime = LocalTime.parse(userInput, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 형식의 입력입니다.");
        }
        return attendanceTime;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private String userInput() {
        return scanner.nextLine();
    }
}
