package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final Scanner sc;

    public InputView() {
        sc = new Scanner(System.in);
    }

    public String readCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        try {
            return LocalTime.parse(sc.nextLine(), TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException();
        }
    }

}
