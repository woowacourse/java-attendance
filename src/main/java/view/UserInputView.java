package view;

import java.util.Scanner;

public class UserInputView {
    public static String askCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return new Scanner(System.in).nextLine();
    }
    public static String askAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요");
        return new Scanner(System.in).nextLine();
    }

}
