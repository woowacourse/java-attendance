package attendance.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InputView {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final String ENTER_NAME = "\n닉네임을 입력해 주세요.\n";
    private static final String ENTER_NAME_FOR_MODIFY = "\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.\n";
    private static final String ENTER_DAY_FOR_MODIFY = "수정하려는 날짜(일)를 입력해 주세요.\n";
    private static final String ENTER_NEW_TIME = "언제로 변경하겠습니까?\n";
    private static final String ENTER_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.\n";

    public static char readOption() {
        String input = readLine();
        if (input.length() != 1) {
            System.out.println("[ERROR] 선택한 기능이 올바르지 않습니다. 다시 입력해주세요.");
            return readOption();
        }
        return input.charAt(0);
    }


    public static String readNickName() {
        System.out.print(ENTER_NAME);
        return readLine();
    }

    public static LocalTime readAttendanceTime() {
        System.out.print(ENTER_ATTENDANCE_TIME);
        try {
            return parseLocalTime();
        } catch (IllegalArgumentException e) {
            return readAttendanceTime();
        }
    }

    public static String readModifyNickName() {
        System.out.print(ENTER_NAME_FOR_MODIFY);
        return readLine();
    }

    public static int readModifyDay() {
        System.out.print(ENTER_DAY_FOR_MODIFY);
        try {
            return Integer.parseInt(readLine());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] 날짜를 숫자로 입력해주세요.");
            return readModifyDay();
        }
    }

    public static LocalTime readModifyTime() {
        System.out.print(ENTER_NEW_TIME);
        try {
            return parseLocalTime();
        } catch (IllegalArgumentException e) {
            return readModifyTime();
        }
    }

    private static String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입출력 오류 발생");
        }
    }

    private static LocalTime parseLocalTime() {
        try {
            return LocalTime.parse(readLine());
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] 잘못된 시간 형식입니다. HH:mm 형식으로 입력해주세요.");
            return readAttendanceTime();
        }
    }


}
