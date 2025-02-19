package attendance.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private static final String TODAY_INFO = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.";

    private final Scanner sc = new Scanner(System.in);

    public String inputOption(LocalDate localDate) {
        System.out.println(TODAY_INFO.formatted(localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getDayOfWeek()));
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
        return userInput();
    }

    public String inputCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return userInput();
    }

    public LocalTime inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String userInput = userInput();
        try {
            return parseStringToLocalTime(userInput);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잚못된 시간 형식입니다.");
        }
    }

    private static LocalTime parseStringToLocalTime(String userInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(userInput, formatter);
    }

    private String userInput() {
        return sc.nextLine();
    }
}
