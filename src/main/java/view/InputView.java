package view;

import controller.Command;
import util.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static controller.Command.*;
import static util.DateTimeUtils.*;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static Command getCommand() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        String commandLine = scanner.nextLine();
        return convertToCommand(commandLine);
    }

    public static String getCrewName() {
        System.out.println("닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public static LocalDateTime getAttendTime() {
        try {
            System.out.println("등교 시간을 입력해 주세요.");
            String input = scanner.nextLine();
            LocalTime time = LocalTime.parse(input, dateTimeFormatter);
            return LocalDateTime.of(TODAY_DATE_NOW, time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("입력 시간의 형식이 옳바르지 않습니다.");
        }
    }

    public static String getEditCrewName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public static LocalDateTime getEditTime() {
        try {
            int editDay = inputValidateEditDay();
            LocalTime time = inputValidateEditTime();
            LocalDate editDate = LocalDate.of(NOW_YEAR, NOW_MONTH, editDay);
            return LocalDateTime.of(editDate, time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("입력 시간의 형식이 옳바르지 않습니다.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("입력한 날짜(일)이 옳바르지 않습니다.");
        }
    }

    private static int inputValidateEditDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String inputName = scanner.nextLine();
        int editDay = Integer.parseInt(inputName);
        validateEditDay(editDay);
        return editDay;
    }

    private static LocalTime inputValidateEditTime() {
        System.out.println("언제로 변경하시겠습니까?");
        String inputTime = scanner.nextLine();
        return LocalTime.parse(inputTime, dateTimeFormatter);
    }

    private static void validateEditDay(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("입력한 날짜(일)이 옳바르지 않습니다.");
        }
        if (LocalDate.of(NOW_YEAR, NOW_MONTH, day).isAfter(TODAY_DATE_NOW)) {
            throw new IllegalArgumentException("미래의 날짜를 수정할 수 없습니다.");
        }
    }
}
