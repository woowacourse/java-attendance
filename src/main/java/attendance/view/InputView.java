package attendance.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import static attendance.view.InputMessage.*;

public class InputView {

    private static final String TIME_FORMAT = "[HH:mm][HH:m][H:mm][H:m]";

    Scanner scanner = new Scanner(System.in);

    public String selectCommand(LocalDate today) {
        System.out.printf(SELECT_COMMAND_TITLE,
                today.getMonthValue(), today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));

        System.out.println(SELECT_COMMAND_MENU);
        return scanner.nextLine().trim();
    }

    public String inputNickname() {
        System.out.println(INPUT_NICKNAME);
        return scanner.nextLine().toLowerCase();
    }

    public LocalTime inputAttendanceTime() {
        try {
            System.out.println(INPUT_ATTENDANCE_TIME);
            String attendanceTimeInput = scanner.nextLine().trim();

            return formatTime(attendanceTimeInput);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(TIME_FORMAT_EXCEPTION);
        }
    }

    public String inputUpdateCrew() {
        System.out.println(INPUT_UPDATE_NICKNAME);
        return scanner.nextLine().trim();
    }

    public int inputUpdateDate() {
        try {
            System.out.println(INPUT_UPDATE_DATE);
            String updateDate = scanner.nextLine().trim();

            return Integer.parseInt(updateDate);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INTEGER_EXCEPTION);
        }
    }

    public LocalTime inputUpdateAttendanceTime() {
        try{
            System.out.println(INPUT_UPDATE_ATTENDANCE_TIME);
            String updateTimeInput = scanner.nextLine().trim();

            return formatTime(updateTimeInput);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(TIME_FORMAT_EXCEPTION);
        }
    }

    private LocalTime formatTime(final String timeInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return LocalTime.parse(timeInput, formatter);
    }
}
