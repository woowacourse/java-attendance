package attendance.view.input;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<DayOfWeek, String> KOREAN_DAYS = Map.of(
        DayOfWeek.MONDAY, "월",
        DayOfWeek.TUESDAY, "화",
        DayOfWeek.WEDNESDAY, "수",
        DayOfWeek.THURSDAY, "목",
        DayOfWeek.FRIDAY, "금",
        DayOfWeek.SATURDAY, "토",
        DayOfWeek.SUNDAY, "일"
    );

    public MenuOption readMenuOption(final LocalDate localDate) {
        System.out.printf("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.%n",
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            KOREAN_DAYS.get(localDate.getDayOfWeek())
        );

        MenuOption.findAll()
            .forEach(menuOption ->
                System.out.println(
                    menuOption.getCode() + ". " + menuOption.getTitle()));

        return MenuOption.from(readInput());
    }

    public String readCrewNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return readInput();
    }

    private String readInput() {
        return scanner.nextLine();
    }
}
