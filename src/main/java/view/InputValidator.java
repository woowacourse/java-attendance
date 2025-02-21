package view;

import domain.AllCrew;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    public static void validateMenu(String menu) {
        if (!menu.matches("[1234Qq]")) {
            throw new IllegalArgumentException("존재하는 메뉴 번호를 입력해주세요.");
        }
    }

    public static void validateName(String name, AllCrew allCrew) {
        if (!allCrew.containsCrewName(name)) {
            throw new IllegalArgumentException("존재하지 않는 닉네임입니다.");
        }
    }

    public static void validateTimeFormat(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바르지 않은 시간 형식입니다.");
        }
    }

    public static void validateDate(String dateInput) {
        try {
            int date = Integer.parseInt(dateInput);
            LocalDate.of(2024, 12, date);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜(일) 입니다.");
        }
    }
}
