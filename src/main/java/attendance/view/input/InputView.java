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
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(
        "HH:mm");

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

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return parseLocalTime(readInput());
    }

    public String readUpdateNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readInput();
    }

    public int readModifyAttendanceDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return parseInt(readInput());
    }

    public LocalTime readModifyAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return parseLocalTime(readInput());
    }

    public String readCrewNickNameForRecord() {
        System.out.println("닉네임을 입력해 주세요.");
        return readInput();
    }

    private int parseInt(final String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 숫자 형식입니다.");
        }
    }

    private LocalTime parseLocalTime(final String input) {
        try {
            return LocalTime.parse(input.trim(), TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다.");
        }
    }

    private String readInput() {
        return scanner.nextLine();
    }
}
