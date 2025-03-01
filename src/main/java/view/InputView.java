package view;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public String getNameInput() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime getAttendTimeInput() {
        System.out.println("등교 시간을 입력해 주세요.");
        try {
            return LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 올바르지 않습니다.");
        }
    }

    public String getOptionInput() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료" + System.lineSeparator());
        return scanner.nextLine();
    }

    public String getEditNameInput() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int getEditDayInput() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return parseDay(scanner.nextLine());
    }

    public LocalTime getEditTimeInput() {
        System.out.println("언제로 변경하겠습니까?");
        try {
            return LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 올바르지 않습니다.");
        }
    }

    private int parseDay(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 입력 형식이 올바르지 않습니다.");
        }
    }
}
