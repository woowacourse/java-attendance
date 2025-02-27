package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputView {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final Scanner sc;

    public InputView() {
        sc = new Scanner(System.in);
    }

    public String readCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        try {
            return LocalTime.parse(sc.nextLine(), TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException();
        }
    }

    public String readUpdateCrewName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public int readUpdateMonthOfDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public LocalTime readUpdateTime(){
        System.out.println("언제로 변경하겠습니까?");
        try {
            return LocalTime.parse(sc.nextLine(), TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
