package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Scanner sc;

    public InputView() {
        sc = new Scanner(System.in);
    }

    public UserCommand readUserCommand() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
        return UserCommand.of(sc.nextLine());
    }

    public String readCrewName() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return sc.nextLine();
    }

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        try {
            return LocalTime.parse(sc.nextLine(), TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식이 잘못되었습니다. HH:mm을 지켜주세요");
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
            throw new IllegalArgumentException("올바른 날짜 형식이 아닙니다.");
        }
    }

    public LocalTime readUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        try {
            return LocalTime.parse(sc.nextLine(), TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식이 잘못되었습니다. HH:mm을 지켜주세요");
        }
    }

}
