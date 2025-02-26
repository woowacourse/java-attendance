package attendance.view;

import attendance.model.Command;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        scanner = new Scanner(System.in);
    }

    public String readCommand(List<Command> commands) {
        System.out.println("기능을 선택해 주세요.");
        printCommands(commands);
        return scanner.nextLine();
    }

    private void printCommands(List<Command> commands) {
        for (Command command : commands) {
            System.out.printf("%s. %s%n", command.getCode(), command.getDescription());
        }
    }

    public String readNickname() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readNicknameForEditAttendance() {
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int readDateForEditAttendance() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return parseInt(scanner.nextLine());
    }

    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다. 입력: %s".formatted(input));
        }
    }

    public String readAttendanceTimeForEditAttendance() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
