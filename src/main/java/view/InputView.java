package view;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final int START_DATE = 1;
    private static final int END_DATE = 31;

    private final Scanner scanner = new Scanner(System.in);

    public String readOption() {
        System.out.println(
                "1. 출석 확인\n" +
                        "2. 출석 수정\n" +
                        "3. 크루별 출석 기록 확인\n" +
                        "4. 제적 위험자 확인\n" +
                        "Q. 종료");

        return readLine();
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }

    public String readName() {
        System.out.println("닉네임을 입력해 주세요");
        String inputName = readLine();
        validateNullOrEmpty(inputName);
        return inputName;
    }

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요");
        String inputTime = readLine();
        validateNullOrEmpty(inputTime);
        return parseToLocalTime(inputTime);
    }

    public String readUpdateName() {
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String inputName = readLine();
        validateNullOrEmpty(inputName);
        return inputName;
    }

    public int readUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String inputDate = readLine();
        validateNullOrEmpty(inputDate);
        return validateDateRange(inputDate);
    }

    public LocalTime readUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        String inputTime = readLine();
        validateNullOrEmpty(inputTime);

        return parseToLocalTime(inputTime);
    }

    private void validateNullOrEmpty(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력할 수 없습니다.");
        }
    }

    private int validateDateRange(final String inputDate) {
        if (Integer.parseInt(inputDate) < START_DATE || Integer.parseInt(inputDate) > END_DATE) {
            throw new IllegalArgumentException("존재하지 않는 날짜(일) 입니다.");
        }
        return Integer.parseInt(inputDate);
    }

    private LocalTime parseToLocalTime(final String input) {
        try {
            return LocalTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("시간은 24시 형식으로 입력해야 합니다.");
        }
    }
}
