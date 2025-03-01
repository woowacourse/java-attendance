package view;

import domain.MenuOption;
import domain.WorkDay;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public MenuOption readMenuOption(LocalDate localDate) {
        System.out.printf("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.%n",
                localDate.getMonth().getValue(), localDate.getDayOfMonth(),
                WorkDay.from(localDate.getDayOfWeek()).getDayOfWeekKorean());
        MenuOption.findAll()
                .forEach(menuOption -> System.out.println(menuOption.getCode() + ". " + menuOption.getDescription()));

        String input = readInput();
        return MenuOption.findByCode(input);
    }

    public String readNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return readInput();
    }

    public LocalTime readArriveTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String arriveTime = readInput();
        return parseToTime(arriveTime);
    }

    private String readInput() {
        return scanner.nextLine();
    }

    public String readUpdateNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readInput();
    }

    public int readUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return parseToInt(readInput());
    }

    public LocalTime readUpdateArriveTime() {
        System.out.println("언제로 변경하겠습니까?");
        String updateArriveTime = readInput();
        return parseToTime(updateArriveTime);
    }

    private LocalTime parseToTime(String rawTime) {
        try {
            return LocalTime.parse(rawTime.trim(), TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다. 올바른 형식 (HH:mm)으로 다시 입력해 주세요.");
        }
    }

    private int parseToInt(String rawInput) {
        try {
            return Integer.parseInt(rawInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 숫자 형식입니다. 숫자로 다시 입력해 주세요.");
        }
    }
}
