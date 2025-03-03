package view;

import constant.MenuOption;

import java.util.Scanner;
import java.util.regex.Pattern;

import static constant.MenuOption.*;
import static constant.ErrorMessage.*;

public class UserInputView {
    public static String askCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return new Scanner(System.in).nextLine();
    }

    public static String askAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요");
        return new Scanner(System.in).nextLine();
    }

    public static String askAttendedDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return new Scanner(System.in).nextLine();
    }

    public static String askTimeForModify() {
        System.out.println("언제로 변경하겠습니까?");
        return new Scanner(System.in).nextLine();
    }

    public static MenuOption askMenuOption() {
        OutputView.printMenu();
        String option = (new Scanner(System.in).nextLine());
        return MenuOption.of(option);
    }
}
