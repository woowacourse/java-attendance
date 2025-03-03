package attendance.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputView {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREA);
    private static final Pattern TIME_INPUT_PATTERN = Pattern.compile("\\d{2}:\\d{2}");
    private final Scanner scanner = new Scanner(System.in);

    public OperationCommand readOperationCommand(final LocalDateTime today) {
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

    public String readAttendanceConfirmTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return readTime();
    }

    public String readAttendanceModificationCrewNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public int readAttendanceModificationDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        try {
            return Integer.parseInt(readLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해 주세요.");
        }
    }

    public String readAttendanceModificationTime() {
        System.out.println("언제로 변경하겠습니까?");
        return readTime();
    }

    private String readLine() {
        String input = scanner.nextLine();
        validateBlank(input);
        return input;
    }

    private String readTime() {
        String attendanceTime = scanner.nextLine();
        validateBlank(attendanceTime);
        validateTimeInputPattern(attendanceTime);
        return attendanceTime;
    }

    private void validateBlank(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("값을 입력해 주세요.");
        }
    }

    private void validateTimeInputPattern(final String timeInput) {
        if (!TIME_INPUT_PATTERN.matcher(timeInput)
                .matches()
        ) {
            throw new IllegalArgumentException("시간은 다음과 같이 입력해주세요. ex) 01:23");
        }
    }

}
