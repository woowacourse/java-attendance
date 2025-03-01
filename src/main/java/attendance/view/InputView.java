package attendance.view;

import attendance.controller.MenuCommand;
import attendance.utility.DateTimeUtility;
import attendance.view.message.InputMessage;
import attendance.view.processor.InputPreprocessor;
import attendance.view.validator.InputValidator;
import java.time.LocalDate;
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
        String input = readInput();
        return MenuCommand.parse(input);
    }

    public String readNickname() {
        System.out.println(InputMessage.NICKNAME.getContent());
        return readInput();
    }

    public LocalTime readArrivalTime() {
        System.out.println(InputMessage.ARRIVAL_TIME.getContent());
        String input = readInput();
        return DateTimeUtility.parseTimeByDefault(input);
    }

    public String readNicknameForUpdate() {
        System.out.println(InputMessage.NICKNAME_FOR_UPDATE.getContent());
        return readInput();
    }

    public int readDayForUpdate() {
        System.out.println(InputMessage.DAY_FOR_UPDATE.getContent());
        String input = readInput();
        InputValidator.validateNonNumeric(input);
        int day = Integer.parseInt(input);
        InputValidator.validateIsInMonth(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
        return day;
    }

    public LocalTime readArrivalTimeForUpdate() {
        System.out.println(InputMessage.TIME_FOR_UPDATE.getContent());
        String input = readInput();
        return DateTimeUtility.parseTimeByDefault(input);
    }

    private String readInput() {
        String input = scanner.nextLine();
        InputValidator.validateBlank(input);
        return InputPreprocessor.removeSideSpace(input);
    }
}
