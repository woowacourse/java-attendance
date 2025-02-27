package attendance.view;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputView {

    private static final Pattern TIME_INPUT_PATTERN = Pattern.compile("\\d{2}:\\d{2}");
    private final Scanner scanner = new Scanner(System.in);

    public String readAttendanceConfirmNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        String nickname = scanner.nextLine();
        validateBlank(nickname);
        return nickname;
    }

    public String readAttendanceConfirmTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String attendanceTime = scanner.nextLine();
        validateBlank(attendanceTime);
        validateTimeInputPattern(attendanceTime);
        return attendanceTime;
    }

    private void validateBlank(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("값을 입력해 주세요.");
        }
    }

    private void validateTimeInputPattern(final String timeInput) {
        if (!TIME_INPUT_PATTERN.matcher(timeInput)
                .matches()
        ) {
            throw new IllegalArgumentException("시간은 다음과 같이 입력해주세요. ex) 01:23");
        }
    }

}
