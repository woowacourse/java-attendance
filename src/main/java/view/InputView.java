package view;

import static view.InputValidator.validateInteger;
import static view.InputValidator.validateTime;

import java.time.LocalDate;
import java.util.Scanner;
import util.DayConverter;

public class InputView {

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String insertCommandType(LocalDate today) {
        System.out.printf("\n오늘은 %d월 %02d일 %s입니다. 기능을 선택해 주세요.\n",
                today.getMonth().getValue(),
                today.getDayOfMonth(),
                DayConverter.getKoreanDayOfWeek(today));

        System.out.print("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
        return readLine();
    }

    public String insertTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String rawTime = readLine();
        validateTime(rawTime);
        return rawTime;
    }

    public String insertChangeName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public int insertChangeDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String rawDayOfMonth = readLine();
        validateInteger(rawDayOfMonth);
        return Integer.parseInt(rawDayOfMonth);
    }

    public String insertChangeTime() {
        System.out.println("언제로 변경하겠습니까?");
        String rawTime = readLine();
        validateTime(rawTime);
        return rawTime;
    }

    public String insertName() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return readLine();
    }
}
