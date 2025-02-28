package attendance.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    public static final String TIME_FORMAT = "[HH:mm][HH:m][H:mm][H:m]";
    Scanner scanner = new Scanner(System.in);

    public String selectCommand(LocalDate today) {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n",
                today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));

        return scanner.nextLine();
    }

    public String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String attendanceTimeInput = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return LocalTime.parse(attendanceTimeInput, formatter);
    }

    public String inputUpdateCrew() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public int inputUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String updateDate = scanner.nextLine();

        return Integer.parseInt(updateDate);
    }

    public LocalTime inputUpdateAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");

    }
}
