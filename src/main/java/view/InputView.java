package view;

import java.time.LocalDate;
import java.util.Scanner;

public final class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public String inputMenuSelector(LocalDate applicationDate) {
        System.out.printf("오늘은 %d월 %d일 금요일입니다. 기능을 선택해 주세요.\n", applicationDate.getMonthValue(), applicationDate.getDayOfMonth());
        System.out.println("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
        return readLine();
    }

    public String inputNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return readLine();
    }

    public String inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return readLine();
    }

    public String inputNickNameForUpdate() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public String inputDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return readLine();
    }

    public String inputUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        return readLine();
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
