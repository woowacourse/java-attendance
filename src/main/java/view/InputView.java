package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {

    public String readCrewName() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public LocalDateTime readTime() {
        Scanner scanner = new Scanner(System.in);
        final String time = scanner.nextLine();
        return LocalDateTime.of(LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth()), LocalTime.parse(time));
    }

}
