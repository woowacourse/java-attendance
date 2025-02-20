package attendance.view;

import attendance.domain.Menu;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    Scanner scanner = new Scanner(System.in);

    public Menu inputMenu(LocalDate now) {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n" +
                "1. 출석 확인\n" +
                "2. 출석 수정\n" +
                "3. 크루별 출석 기록 확인\n" +
                "4. 제적 위험자 확인\n" +
                "Q. 종료\n", now.getMonthValue(), now.getDayOfMonth(), now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        return Menu.of(scanner.nextLine());
    }

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int inputUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(scanner.nextLine());
    }

    public String inputUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
