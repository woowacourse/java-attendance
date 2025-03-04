package attendance.io.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {

    private static final String INPUT_COMMAND_FORMAT = """
            오늘은 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    private final Scanner scanner = new Scanner(System.in);

    public Command inputCommand(LocalDate today) {
        System.out.printf(INPUT_COMMAND_FORMAT, today.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")));
        return Command.from(readLine());
    }

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readLine();
    }

    public LocalTime inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String rawAttendanceTime = readLine();
        return toLocalTime(rawAttendanceTime);
    }

    public String inputNicknameForAttendanceUpdate() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public int inputUpdateDateOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return toDateOfMonth(readLine());
    }

    public LocalTime inputUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        return toLocalTime(readLine());
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }

    private int toDateOfMonth(String value) {
        try {
            int date = Integer.parseInt(value);
            validateDateOfMonth(date);
            return date;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력하세요");
        }
    }

    private void validateDateOfMonth(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("날짜는 1일 이상 31일 이하여야 합니다.");
        }
    }

    private LocalTime toLocalTime(String rawAttendanceTime) {
        try {
            return LocalTime.parse(rawAttendanceTime, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식은 HH:mm이어야 합니다.");
        }
    }
}
