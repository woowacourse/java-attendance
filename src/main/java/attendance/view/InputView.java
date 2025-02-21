package attendance.view;

import static attendance.view.exception.InputExceptionMessage.DATE_NOT_INTEGER;
import static attendance.view.exception.InputExceptionMessage.INVALID_DATE_RANGE;
import static attendance.view.message.InputMessage.INPUT_ATTEND_TIME;
import static attendance.view.message.InputMessage.INPUT_NICKNAME;
import static attendance.view.message.InputMessage.INPUT_UPDATE_DATE;
import static attendance.view.message.InputMessage.INPUT_UPDATE_TIME;
import static attendance.view.message.InputMessage.START_MESSAGE;

import attendance.domain.Menu;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    public static final int START_DAY_OF_MONTH = 1;

    Scanner scanner = new Scanner(System.in);

    public Menu inputMenu(LocalDate now) {
        System.out.printf(START_MESSAGE, now.getMonthValue(), now.getDayOfMonth(), now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        return Menu.of(scanner.nextLine());
    }

    public String inputNickname() {
        System.out.println(INPUT_NICKNAME);
        return scanner.nextLine();
    }

    public String inputAttendTime() {
        System.out.println(INPUT_ATTEND_TIME);
        return scanner.nextLine();
    }

    public int inputUpdateDate(LocalDate now) {
        try {
            System.out.println(INPUT_UPDATE_DATE);
            int date = Integer.parseInt(scanner.nextLine());
            validateDayOfMonth(date, now);
            return date;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(DATE_NOT_INTEGER);
        }
    }

    public String inputUpdateTime() {
        System.out.println(INPUT_UPDATE_TIME);
        return scanner.nextLine();
    }

    private void validateDayOfMonth(int number, LocalDate date) {
        if (number >= START_DAY_OF_MONTH && number > date.lengthOfMonth()) {
            throw new IllegalArgumentException(String.format(INVALID_DATE_RANGE, START_DAY_OF_MONTH, date.lengthOfMonth()));
        }
    }
}
