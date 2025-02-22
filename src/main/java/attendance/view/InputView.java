package attendance.view;

import static attendance.view.InputValidator.validateIsNumeric;

import attendance.utility.DateTimeParser;
import attendance.view.message.InputMessage;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {

    private static final Scanner console = new Scanner(System.in);

    public String readMenuCommand() {
        return console.nextLine();
    }

    public String readNickname() {
        System.out.println(InputMessage.NICK_NAME.getContent());
        return console.nextLine();
    }

    public LocalTime readArrivalTime() {
        System.out.println(InputMessage.ARRIVAL_TIME.getContent());
        return DateTimeParser.parseTime(console.nextLine());
    }

    public String readNicknameForUpdate() {
        System.out.println(InputMessage.NICK_NAME_FOR_UPDATE.getContent());
        return console.nextLine();
    }

    public LocalTime readArrivalTimeForUpdate() {
        System.out.println(InputMessage.ARRIVAL_TIME_FOR_UPDATE.getContent());
        return DateTimeParser.parseTime(console.nextLine());
    }

    public int readDateForUpdate() {
        System.out.println(InputMessage.DATE_FOR_UPDATE.getContent());
        String input = console.nextLine();
        validateIsNumeric(input);
        return Integer.parseInt(input);
    }
}
