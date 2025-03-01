package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readMenuSelection() {
        System.out.println("오늘은 12월 13일 금요일입니다. 기능을 선택해 주세요." + System.lineSeparator() +
                "1. 출석 확인" + System.lineSeparator() +
                "2. 출석 수정" + System.lineSeparator() +
                "3. 크루별 출석 기록 확인" + System.lineSeparator() +
                "4. 제적 위험자 확인" + System.lineSeparator() +
                "Q. 종료");
        return scanner.nextLine().toUpperCase();
    }

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readCheckInTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine();
        validateTime(input);
        return input;
    }

    public String readUpdateNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = scanner.nextLine();
        validateDate(input);
        return input;
    }

    public String readUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine();
        validateTime(input);
        return input;
    }

    private void validateDate(String input) {
        try {
            LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 유효한 날짜를 입력해 주세요." + System.lineSeparator());
        }
    }

    private void validateTime(String input) {
        try {
            LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간은 HH:mm 형식으로 입력해 주세요." + System.lineSeparator());
        }
    }
}
