import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String readOption() {
        int date = LocalDate.now().getDayOfMonth();
        LocalDate now = LocalDate.of(2024, 12, date);
        String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("오늘은 12월 %d일 %s입니다. 기능을 선택해 주세요.%n", now.getDayOfMonth(), dayOfWeek);
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");

        return SCANNER.next();
    }

    public static String readName() {
        System.out.println("닉네임을 입력해 주세요.");
        return SCANNER.next();
    }

    public static String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return SCANNER.next();
    }
}
