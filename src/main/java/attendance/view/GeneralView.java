package attendance.view;

import attendance.controller.FeatureCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class GeneralView {

    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREA);
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREA);

    public void printExceptionMessage(final String exceptionMessage) {
        System.out.println("[ERROR] " + exceptionMessage);
        System.out.println();
    }

    public FeatureCommand readCommandWithToday(final LocalDate today) {
        System.out.println("오늘은 %s입니다. 기능을 선택해 주세요.".formatted(DATE_FORMATTER.format(today)));
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
        String commandText = readOneLine();
        return FeatureCommand.from(commandText);
    }

    static String readOneLine() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
