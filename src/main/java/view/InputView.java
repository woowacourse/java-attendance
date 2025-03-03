package view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Menu readMenu() {
        String input = scanner.nextLine();
        return Menu.from(input);
    }

    public String readNickname() {
        System.out.printf("%n닉네임을 입력해 주세요.%n");
        return scanner.nextLine();
    }

    public LocalTime readCheckInTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine();
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간은 HH:mm 형식으로 입력해 주세요.");
        }
    }

    public String readUpdateNickname() {
        System.out.printf("%n출석을 수정하려는 크루의 닉네임을 입력해 주세요.%n");
        return scanner.nextLine();
    }

    public LocalDate readUpdateDate() {
        int month = readUpdateMonth();
        int day = readUpdateDay();
        try {
            return LocalDate.of(2025, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일은 존재하지 않는 날짜입니다.", month, day));
        }
    }

    private int readUpdateMonth() {
        System.out.printf("수정하려는 날짜(월)를 입력해주세요.%n");
        String input = scanner.next();
        validatePositiveNumber(input);
        return Integer.parseInt(input);
    }

    private int readUpdateDay() {
        System.out.printf("수정하려는 날짜(일)를 입력해주세요.%n");
        String input = scanner.next();
        validatePositiveNumber(input);
        return Integer.parseInt(input);
    }

    private void validatePositiveNumber(String input) {
        String POSITIVE_INTEGER_REGEX = "[1-9]\\d*";
        if (!input.matches(POSITIVE_INTEGER_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해 주세요.");
        }
    }
}
