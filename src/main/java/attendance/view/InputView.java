package attendance.view;

import java.util.Scanner;

public class InputView {

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return readLine();
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
