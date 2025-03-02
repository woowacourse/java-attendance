package view;

import domain.AttendanceCommand;
import java.time.LocalDate;
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

    public AttendanceCommand readAttendanceCommand() {
        return AttendanceCommand.findByCommand(readLine());
    }

    public LocalDate readDate() {
        return DateTimeParser.parseToLocalDate(readLine());
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
