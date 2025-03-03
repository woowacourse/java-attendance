package view;

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
}
