package view;

import model.Command;

import java.util.Scanner;

public class InputView {

    private static final Scanner sc = new Scanner(System.in);

    public static String readCommand(final DateInfoDto dto) {
        System.out.println(String.format("오늘은 %s입니다. 기능을 선택해 주세요.", dto.getFormattedDate()));
        readCommandIntro();
        return sc.nextLine();
    }

    private static void readCommandIntro() {
        for (final Command command : Command.values()) {
            System.out.println(String.format("%s. %s", command.getSymbol(), command.getDisplayName()));
        }
    }

    public static String readNickname() {
        System.out.println();
        System.out.println("닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public static String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return sc.nextLine();
    }

    public static String readNicknameByUpdate() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public static String readDayOfMonthByUpdate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return sc.nextLine();
    }

    public static String readAttendanceTimeByUpdate() {
        System.out.println("언제로 변경하겠습니까?");
        return sc.nextLine();
    }
}
