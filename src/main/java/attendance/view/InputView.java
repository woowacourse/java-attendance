package attendance.view;

import attendance.controller.MenuCommand;
import attendance.utility.DateTimeUtility;
import attendance.view.message.InputMessage;
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
        String input = scanner.nextLine();
        return MenuCommand.parse(input);
    }

    public String readNickname() {
        System.out.println(InputMessage.NICKNAME.getContent());
        return scanner.nextLine();
    }

    public LocalTime readArrivalTime() {
        System.out.println(InputMessage.ARRIVAL_TIME.getContent());
        return DateTimeUtility.parseTimeByDefault(scanner.nextLine());
    }

    public String readNicknameForUpdate() {
        System.out.println(InputMessage.NICKNAME_FOR_UPDATE.getContent());
        return scanner.nextLine();
    }

    public int readDayForUpdate() {
        System.out.println(InputMessage.DAY_FOR_UPDATE.getContent());
        return Integer.parseInt(scanner.nextLine());
    }

    public LocalTime readArrivalTimeForUpdate() {
        System.out.println(InputMessage.TIME_FOR_UPDATE.getContent());
        return DateTimeUtility.parseTimeByDefault(scanner.nextLine());
    }
}
