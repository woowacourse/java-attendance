package attendance.view;

import attendance.util.Util;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String readOption() {
        System.out.println("오늘은 12월 16일 월요일입니다. 기능을 선택해 주세요.\n"
                + "1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");

        return scanner.nextLine();
    }

    public String readName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime readEntryTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return Util.parseToTime(scanner.nextLine());
    }

    public String readModifyName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int readModifyDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Util.parseToInt(scanner.nextLine());
    }

    public LocalTime readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        return Util.parseToTime(scanner.nextLine());
    }

}
