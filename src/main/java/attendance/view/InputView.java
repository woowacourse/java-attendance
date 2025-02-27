package attendance.view;

import static attendance.util.DateFormatUtil.DATE_FORMATTER;

import attendance.domain.CustomClock;
import attendance.util.DateFormatUtil;
import attendance.util.Parser;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;
    private final CustomClock clock;

    public InputView(CustomClock clock) {
        this.clock = clock;
        this.scanner = new Scanner(System.in);
    }

    public String readOption() {
        System.out.printf("""
                오늘은 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, clock.nowDate().format(DATE_FORMATTER));

        return scanner.nextLine();
    }

    public String readName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime readEntryTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return DateFormatUtil.parseToTime(scanner.nextLine());
    }

    public String readModifyName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int readModifyDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Parser.parseToInt(scanner.nextLine());
    }

    public LocalTime readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        return DateFormatUtil.parseToTime(scanner.nextLine());
    }

}
