package view;

import view.parser.TimeParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputMenu(LocalDate today) {
        String koreanDayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf(ViewMessage.SELECT_MENU_INTRO, today.getMonth().getValue(), today.getDayOfMonth(),
                koreanDayOfWeek);
        System.out.println(ViewMessage.SELECT_MENU);

        return scanner.nextLine();
    }

    public String inputNickname() {
        System.out.println(ViewMessage.NICKNAME);

        return scanner.nextLine();
    }

    public LocalTime inputTime() {
        System.out.println(ViewMessage.ATTENDANCE_TIME);

        String time = scanner.nextLine();
        return TimeParser.validateTimeFormat(time);
    }

    public String inputUpdateNickname() {
        System.out.println(ViewMessage.UPDATE_NICKNAME);

        return scanner.nextLine();
    }

    public String inputUpdateDate() {
        System.out.println(ViewMessage.UPDATE_DATE);

        return scanner.nextLine();
    }

    public String inputUpdateTime() {
        System.out.println(ViewMessage.UPDATE_WHEN);

        return scanner.nextLine();
    }

}
