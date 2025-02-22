package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String inputCommand(LocalDate today) {
        System.out.printf("오늘은 %s 입니다. 기능을 선택해 주세요.%n", today.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")));
        System.out.println("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    public String inputNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    public String inputEditNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    public LocalDate inputDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        validateDateInput(input);
        return parseDate(input);
    }

    public LocalTime inputTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        validateTimeInput(input);
        return parseTime(input);
    }

    public LocalTime inputEditTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        validateTimeInput(input);
        return parseTime(input);
    }

    private LocalDate parseDate(String input) {
        validateDateInput(input);
        return LocalDate.of(2024, 12, Integer.parseInt(input));
    }

    private LocalTime parseTime(String input) {
        validateTimeInput(input);
        String[] parse = input.split(":");
        return LocalTime.of(Integer.parseInt(parse[0]), Integer.parseInt(parse[1]));
    }

    private void validateEmptyInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("공백은 입력할 수 없습니다.");
        }
    }

    private void validateIntInput(String input) {
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("숫자만 입력 가능");
        }
    }

    private void validateDateInput(String input) {
        validateIntInput(input);
        int day = Integer.parseInt(input);
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("1-31만 가능");
        }
    }

    private void validateTimeInput(String input) {
        String[] parse = input.strip().split(":");
        if (parse.length < 2) {
            throw new IllegalArgumentException("입력 형식이 잘못되었습니다.");
        }
        Arrays.stream(parse)
                .forEach(this::validateIntInput);
        int hour = Integer.parseInt(parse[0]);
        int minute = Integer.parseInt(parse[1]);
        validateHour(hour);
        validateMinute(minute);
    }

    private void validateHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("hour는 0-23만 가능");
        }
    }

    private void validateMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("minute는 0-59만 가능");
        }
    }
}
