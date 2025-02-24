package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final LocalDate today;
    private final Scanner scanner;

    public InputView(LocalDate today, Scanner scanner) {
        this.today = today;
        this.scanner = scanner;
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

        return scanner.nextLine();
    }

    public String askNickNameForCheckAttendance() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String askAttendanceTimeForCheckAttendance() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String askNickNameForModifyAttendanceInfo() {
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String askDayForModifyAttendanceInfo() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String askAttendanceTimeForModifyAttendance() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
