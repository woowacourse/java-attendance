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

    public int readDayOfMonth() {
        Scanner scanner = new Scanner(System.in);
        final String time = scanner.nextLine();
        return Integer.parseInt(time);
    }

    public LocalTime readTime() {
        Scanner scanner = new Scanner(System.in);
        final String time = scanner.nextLine();
        return LocalTime.parse(time);
    }

}
