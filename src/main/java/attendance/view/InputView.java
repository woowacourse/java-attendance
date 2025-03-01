package attendance.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private static final String NICKNAME_INPUT_MESSAGE = "닉네임을 입력해 주세요.";
    private static final String ATTENDANCE_TIME_INPUT_MESSAGE = "등교시간을 입력해주세요.";
    private static final String TODAY_DATE_MESSAGE = "오늘은 %s월 %s일 %s입니다. 기능을 선택해 주세요.";
    private static final String CHOOSE_FUNCTION_MESSAGE = "1. 출석 확인\n"
        + "2. 출석 수정\n"
        + "3. 크루별 출석 기록 확인\n"
        + "4. 제적 위험자 확인\n"
        + "Q. 종료";

    private final Scanner scanner = new Scanner(System.in);

    private InputView() {}

    public static InputView create() {
        return new InputView();
    }

    public String inputNickname() {
        printMessage(NICKNAME_INPUT_MESSAGE);
        return userInput();
    }

    public String inputFunction() {
        System.out.println(CHOOSE_FUNCTION_MESSAGE);
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

    public void todayDateMessage(LocalDate nowDate) {
        System.out.println(TODAY_DATE_MESSAGE.formatted(
            nowDate.getMonthValue(), nowDate.getDayOfMonth(),
            nowDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA))
        );
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private String userInput() {
        return scanner.nextLine();
    }
}
