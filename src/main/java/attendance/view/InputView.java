package attendance.view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String inputCommand(LocalDate today) {
        System.out.printf("오늘은 %02d월 %02d일 %s요일입니다. 기능을 선택해 주세요.%n", today.getMonth().getValue(), today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
        System.out.println("1. 출석 확인\n2. 출석 수정\n3. 크루별 출석 기록 확인\n4. 제적 위험자 확인\nQ. 종료");
        return trim(scanner.nextLine());
    }

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return trim(scanner.nextLine());
    }

    public String inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return trim(scanner.nextLine());
    }

    private String trim(String input) {
        return input.replaceAll(" ", "");
    }
}
