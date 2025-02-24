package view;

import domain.Operation;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        scanner = new Scanner(System.in);
    }

    public String readCrewName() {
        return scanner.nextLine();
    }

    public int readDayOfMonth() {
        final String time = scanner.nextLine();
        try {
            return Integer.parseInt(time);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 숫자 입력입니다. 숫자를 입력하여 주세요.");
        }
    }

    public LocalTime readTime() {
        final String time = scanner.nextLine();
        try {
            return LocalTime.parse(time);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("유효하지 않은 시간 입력입니다. ('HH:mm')으로 입력해주세요.");
        }
    }

    public Operation readChoiceOperation() {
        return Operation.of(scanner.nextLine());
    }

}
