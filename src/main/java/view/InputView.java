package view;

import java.time.LocalDate;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readCommandCode(LocalDate date) {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.\n", CustomDateTimeFormatter.dateToString(date));
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
        return scanner.next();
    }

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public String readTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.next();
    }

    public String readNicknameForModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public String readModifyDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.next();
    }

    public String readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.next();
    }
}
