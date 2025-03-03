package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readCommand(LocalDate date) {
        System.out.printf("오늘은 %d월 %s일 %s입니다. 기능을 선택해 주세요." + System.lineSeparator(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));

        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        return scanner.nextLine();
    }

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime readTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String time = scanner.nextLine();
        return LocalTime.of(
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1])
        );
    }
}
