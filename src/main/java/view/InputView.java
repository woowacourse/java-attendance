package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {
    private static final String NICKNAME_INPUT_MESSAGE = "닉네임을 입력해 주세요.";
    private static final String ATTENDANCE_INPUT_MESSAGE = "등교 시간을 입력해 주세요.";
    private static final String EDIT_NICKNAME_INPUT_MESSAGE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String EDIT_DAY_OF_MONTH_INPUT_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String ASK_NEW_TIME_FOR_CHANGE = "언제로 변경하겠습니까?";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    private final Scanner scanner = new Scanner(System.in);

    private static Integer convertStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }

    public String getNickname() {
        System.out.println(NICKNAME_INPUT_MESSAGE);
        return scanner.nextLine();
    }

    public LocalTime getAttendanceTime() {
        System.out.println(ATTENDANCE_INPUT_MESSAGE);
        return convertStringToLocalTime(scanner.nextLine());
    }

    public String getEditNickname() {
        System.out.println(EDIT_NICKNAME_INPUT_MESSAGE);
        return scanner.nextLine();
    }

    public Integer getEditDayOfMonth() {
        System.out.println(EDIT_DAY_OF_MONTH_INPUT_MESSAGE);
        return convertStringToInteger(scanner.nextLine());
    }

    public LocalTime getNewTime() {
        System.out.println(ASK_NEW_TIME_FOR_CHANGE);
        return convertStringToLocalTime(scanner.nextLine());
    }

    public String getOption() {
        return scanner.nextLine();
    }

    private LocalTime convertStringToLocalTime(String timeInput) {
        try {
            return LocalTime.parse(timeInput, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력된 시간 형식이 적절하지 않습니다.");
        }
    }
}
