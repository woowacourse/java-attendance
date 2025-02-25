package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class UserInputView {
    private final LocalDate today;

    public UserInputView(LocalDate today) {
        this.today = today;
    }

    public String askMenu() {
        String str = "오늘은 " + today.format(DateTimeFormatter.ofPattern("MM월 dd일 ")) +
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + "입니다. ";
        str += "기능을 선택해 주세요.";
        System.out.println(str);
        String menu = "1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료";
        System.out.println(menu);

        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String askNickNameForCheckAttendanceInfo() {
        System.out.println("\n닉네임을 입력해 주세요.");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("\n이번 달 " + name + "의 출석 기록입니다.\n");
        return name;
    }

    public String askNickNameForCheckAttendance() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return new Scanner(System.in).nextLine();
    }

    public ArrayList<String> askAttendanceTimeForCheckAttendance() {
        System.out.println("등교 시간을 입력해 주세요.");
        return new ArrayList<>(Arrays.asList(new Scanner(System.in).nextLine().split(":")));
    }

    public String askNickNameForModifyAttendanceInfo() {
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return new Scanner(System.in).nextLine();
    }

    public int askDayForModifyAttendanceInfo() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    public ArrayList<String> askAttendanceTimeForModifyAttendance() {
        System.out.println("언제로 변경하겠습니까?");
        return new ArrayList<>(Arrays.asList(new Scanner(System.in).nextLine().split(":")));
    }
}
