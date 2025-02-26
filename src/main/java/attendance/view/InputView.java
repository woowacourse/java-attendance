package attendance.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {
    Scanner scanner = new Scanner(System.in);

    public String readSelectMenu(final LocalDate today) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd EEEE");
        System.out.printf("오늘은 %s입니다. 기능을 선택해주세요.\n", dateTimeFormatter.format(today));
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
        return scanner.nextLine();
    }

    public String readAttendNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readUpdateAttendanceNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readUpdateAttendanceDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readUpdateAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
