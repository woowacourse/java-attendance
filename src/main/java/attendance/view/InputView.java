package attendance.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readAttendanceConfirmNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        String nickname = scanner.nextLine();
        validateBlank(nickname);
        return nickname;
    }

    private void validateBlank(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("값을 입력해 주세요.");
        }
    }

}
