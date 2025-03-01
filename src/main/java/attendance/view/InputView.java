package attendance.view;

import attendance.model.SystemDuration;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private final DateTimeFormatter normalFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE");
    private static Scanner scanner = new Scanner(System.in);

    public String inputCommand() {
        System.out.println(
                String.format("""
                        오늘은 %s입니다. 기능을 선택해 주세요.
                        1. 출석 확인
                        2. 출석 수정
                        3. 크루별 출석 기록 확인
                        4. 제적 위험자 확인
                        Q. 종료
                        """, SystemDuration.getNow().format(normalFormatter))
        );
        return scanner.nextLine();
    }

    public String inputCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputModifyAttendanceCrewName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputModifyAttendanceDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputModifyAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }

    public String inputEntryTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }
}
