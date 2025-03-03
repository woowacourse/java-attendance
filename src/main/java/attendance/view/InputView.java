package attendance.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREA);
    private final Scanner scanner = new Scanner(System.in);
    private final LocalDate today;

    public InputView(final LocalDate today) {
        this.today = today;
    }

    public OperationCommand readOperationCommand(final LocalDateTime today) {
        System.out.println();
        System.out.println(String.join("", "오늘은 ", DATE_FORMATTER.format(today), "입니다. 기능을 선택해 주세요."));
        System.out.print("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
        String operationCommand = scanner.nextLine();
        validateBlank(operationCommand);
        return OperationCommand.from(operationCommand);
    }

    public String readAttendanceConfirmNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readLine();
    }

    public LocalTime readAttendanceConfirmTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return readTime();
    }

    public String readAttendanceModificationCrewNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public LocalDate readAttendanceModificationDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        try {
            return today.withDayOfMonth(Integer.parseInt(readLine()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("알맞은 날짜를 입력해 주세요.");
        }
    }

    public LocalTime readAttendanceModificationTime() {
        System.out.println("언제로 변경하겠습니까?");
        return readTime();
    }

    private String readLine() {
        String input = scanner.nextLine();
        validateBlank(input);
        return input;
    }

    private LocalTime readTime() {
        try {
            String attendanceTime = readLine();
            return LocalTime.parse(attendanceTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간은 다음과 같이 입력해주세요. ex) 09:00, 09:03, 10:00");
        }
    }

    private void validateBlank(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("값을 입력해 주세요.");
        }
    }

}
