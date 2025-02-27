package attendance.view;

import attendance.controller.MenuCommand;
import attendance.utility.DateTimeUtility;
import attendance.view.message.InputMessage;
import attendance.view.validator.InputValidator;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public MenuCommand readMenuCommand(LocalDateTime now) {
        String dateContent = DateTimeUtility.formatDateTime(now);
        String menuContent = String.format(InputMessage.MENU.getContent(), dateContent);
        System.out.println(menuContent);
        String input = readNotBlankInput();
        return MenuCommand.parse(input);
    }

    public String readNickname() {
        System.out.println(InputMessage.NICKNAME.getContent());
        String input = readNotBlankInput();
        return input;
    }

    public LocalTime readArrivalTime() {
        System.out.println(InputMessage.ARRIVAL_TIME.getContent());
        String input = readNotBlankInput();
        return DateTimeUtility.parseTimeByDefault(input);
    }

    public String readNicknameForUpdate() {
        System.out.println(InputMessage.NICKNAME_FOR_UPDATE.getContent());
        String input = readNotBlankInput();
        return input;
    }

    public int readDayForUpdate() {
        System.out.println(InputMessage.DAY_FOR_UPDATE.getContent());
        String input = readNotBlankInput();
        InputValidator.validateNonNumeric(input);
        return Integer.parseInt(input);
    }

    public LocalTime readArrivalTimeForUpdate() {
        System.out.println(InputMessage.TIME_FOR_UPDATE.getContent());
        String input = readNotBlankInput();
        return DateTimeUtility.parseTimeByDefault(input);
    }

    private String readNotBlankInput() {
        String input = scanner.nextLine();
        InputValidator.validateBlank(input);
        return input;
    }
}
