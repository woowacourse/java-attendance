package view;

import java.util.Scanner;

import static domain.util.DateUtil.TODAY;

public class InputView {
    Scanner scanner;
    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputMenu() {
        return inputByMessage(String.format("""
                오늘은 %d월 %2d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, TODAY.getMonth().getValue(), TODAY.getDayOfMonth(), ViewUtil.getDayOfWeekToMessage(TODAY.getDayOfWeek())));
    }
    public String inputByMessage(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public String inputNickName() {
        return inputByMessage("닉네임을 입력해 주세요.\n");
    }

    public String inputAttendTime() {
        return inputByMessage("등교 시간을 입력해 주세요.\n");
    }
}
