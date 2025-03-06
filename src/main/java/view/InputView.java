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

    public String inputName() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine();
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간은 HH:mm 형식으로 입력해 주세요.");
        }
    }

    public String inputEditName() {
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalDate inputDateForEdit() {
        int month = readUpdateMonth();
        int day = readUpdateDay();
        try {
            return LocalDate.of(2025, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일은 존재하지 않는 날짜입니다.", month, day));
        }
    }

    private int readUpdateMonth() {
        System.out.println("수정하려는 날짜(월)를 입력해주세요.");
        String input = scanner.nextLine();
        validatePositiveNumber(input);
        return Integer.parseInt(input);
    }

    private int readUpdateDay() {
        System.out.println("수정하려는 날짜(일)를 입력해주세요.");
        String input = scanner.nextLine();
        validatePositiveNumber(input);
        return Integer.parseInt(input);
    }

    public LocalTime inputTimeForEdit() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine();
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간은 HH:mm 형식으로 입력해 주세요.");
        }
    }

    private void validatePositiveNumber(String input) {
        String regex = "[1-9]\\d*";
        if (!input.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해 주세요.");
        }
    }
}
