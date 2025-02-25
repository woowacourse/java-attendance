package view;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public String getAttendNameInput() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime getAttendTimeInput() {
        System.out.println("등교 시간을 입력해 주세요.");
        try {
            return LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 올바르지 않습니다.");
        }
    }
}
