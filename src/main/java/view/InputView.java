package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public Integer readTodayDate() {
        System.out.println("오늘은 2월 며칠인가요? 일자를 입력해주세요.");
        return convertStringToInteger(scanner.nextLine());
    }

    public String readNickname() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return LocalTime.parse(scanner.nextLine(), formatter);
    }

    public String readUpdateNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int readUpdateDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return convertStringToInteger(scanner.nextLine());
    }

    public LocalTime readUpdateAttendanceTime() {
        System.out.println("언제로 변경하시겠습니까?");
        return LocalTime.parse(scanner.nextLine(), formatter);
    }

    public String readOptionNumber() {
        return scanner.nextLine();
    }

    private Integer convertStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다.");
        }
    }
}
