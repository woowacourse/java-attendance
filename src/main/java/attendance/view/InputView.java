package attendance.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner console = new Scanner(System.in);

    public Menu readMenuCommand() {
        return Menu.find(console.nextLine());
    }

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return console.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return console.nextLine();
    }
}
