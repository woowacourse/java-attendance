package attendance.view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private Scanner scanner = new Scanner(System.in);

    public OperationCommand readOperationCommand() {
        String commandText = scanner.nextLine();
        return OperationCommand.from(commandText);
    }

    public String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime readAttendanceTime() {
        try {
            System.out.println("등교 시간을 입력해 주세요.");
            String input = scanner.nextLine();
            return LocalTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 입력 형식이 아닙니다.");
        }
    }

}
