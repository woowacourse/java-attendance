package view;

import controller.Menu;
import domain.CustomDate;
import exception.InvalidInputException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String readName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine().trim();
    }

    public LocalTime readTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine().trim();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("시간은 HH:mm 형식으로 입력해야 합니다.");
        }
    }

    public Menu readMenu() {
        return Menu.of(scanner.nextLine().trim());
    }

    public LocalDate readModifyDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        final int day = Integer.parseInt(scanner.nextLine().trim());
        return CustomDate.now()
                .withDayOfMonth(day);
    }

    public LocalTime readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(input, formatter);
    }
}
