package view;

import domain.Current;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String inputCommand(final LocalDate today) {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n", DateTimeFormat.DATE.formatDate(today));
        System.out.println("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료""");
        return inputString();
    }

    public String inputName() {
        System.out.println("닉네임을 입력해 주세요.");
        return inputString();
    }

    private String inputString() {
        String input = scanner.nextLine();
        checkEmptyInput(input);
        return input;
    }

    public LocalDate inputDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = inputString();
        try {
            int day = Integer.parseInt(input);
            return LocalDate.of(Current.TODAY.getYear(), Current.TODAY.getMonth(), day);
        } catch (NumberFormatException | DateTimeException e) {
            throw new IllegalArgumentException("잘못된 날짜 입력입니다");
        }
    }

    public LocalTime inputAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return inputTime();
    }

    public LocalTime inputChangeTime() {
        System.out.println("언제로 변경하겠습니까?");
        return inputTime();
    }

    private LocalTime inputTime() {
        String input = inputString();
        try {
            return LocalTime.parse(input);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("잘못된 시간 입력입니다");
        }
    }

    private void checkEmptyInput(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 입력입니다.");
        }
    }

    public void close() {
        this.scanner.close();
    }
}
