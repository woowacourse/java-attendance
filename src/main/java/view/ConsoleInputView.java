package view;

import java.time.LocalTime;
import java.util.Scanner;
import util.DateTimeParser;

public class ConsoleInputView {

    public String readCrewName() {
        return readLine();
    }

    public LocalTime readTime() {
        return DateTimeParser.parseToLocalTime(readLine());
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
