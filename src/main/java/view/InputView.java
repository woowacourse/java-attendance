package view;

import view.parser.DayParser;
import view.parser.TimeParser;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import static config.AppConfig.TODAY;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputMenu() {
        String koreanDayOfWeek = TODAY.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf(ViewMessage.SELECT_MENU_INTRO, TODAY.getMonth().getValue(), TODAY.getDayOfMonth(),
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

        return TimeParser.validateTimeFormat(scanner.nextLine());
    }

    public String inputUpdateNickname() {
        System.out.println(ViewMessage.UPDATE_NICKNAME);

        return scanner.nextLine();
    }

    public int inputUpdateDate() {
        System.out.println(ViewMessage.UPDATE_DATE);

        return DayParser.validateDayFormat(scanner.nextLine());
    }

    public LocalTime inputUpdateTime() {
        System.out.println(ViewMessage.UPDATE_WHEN);

        return TimeParser.validateTimeFormat(scanner.nextLine());
    }

}
