package view;

import domain.Operation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        return Integer.parseInt(time);
    }

    public LocalTime readTime() {
        final String time = scanner.nextLine();
        return LocalTime.parse(time);
    }

    public Operation readChoiceOperation() {
        return Operation.of(scanner.nextLine());
    }

}
