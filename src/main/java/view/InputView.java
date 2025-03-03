package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

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

    private void validateNullOrEmpty(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력할 수 없습니다.");
        }
    }

    private LocalTime parseToLocalTime(final String input) {
        return LocalTime.parse(input, DATE_TIME_FORMATTER);
    }
}
