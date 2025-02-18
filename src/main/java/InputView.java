import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void readCommand() {
        LocalDate today = LocalDate.now();
        System.out.println(String.format("오늘은 %d월 %s일 %s입니다. 기능을 선택해 주세요.\n" +
                "1. 출석 확인\n" +
                "2. 출석 수정\n" +
                "3. 크루별 출석 기록 확인\n" +
                "4. 제적 위험자 확인\n" +
                "Q. 종료", today.getMonthValue(), today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        ));
        scanner.nextLine();
    }

}
