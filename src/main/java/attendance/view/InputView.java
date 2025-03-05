package attendance.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner console = new Scanner(System.in);

    public Menu readMenuCommand() {
        return Menu.find(console.nextLine());
    }

    public String readNickname() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return console.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return console.nextLine();
    }

    public String readNicknameForUpdate() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return console.nextLine();
    }

    public int readAttendanceDayForUpdate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(console.nextLine());
    }

    public String readAttendanceTimeForUpdate() {
        System.out.println("언제로 변경하겠습니까?");
        return console.nextLine();
    }
}
