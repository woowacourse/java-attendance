package attendance.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private static final String INPUT_COMMAND_FORMAT =
            "오늘은 %s입니다. 기능을 선택해 주세요.\n"
                    + "1. 출석 확인\n"
                    + "2. 출석 수정\n"
                    + "3. 크루별 출석 기록 확인\n"
                    + "4. 제적 위험자 확인\n"
                    + "Q. 종료\n";

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
        return LocalTime.parse(rawAttendanceTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }
}
