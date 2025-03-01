package attendance.view;

import attendance.view.constant.CommandOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public CommandOption readCommandOption(LocalDateTime now) {
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        String dayName = now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA);

        System.out.println(String.format("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.", month, day, dayName));
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
        return CommandOption.from(scanner.nextLine());
    }

    public String readCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalDateTime readAttendanceTime(LocalDateTime currentDateTime) {
        System.out.println("등교 시간을 입력해 주세요.");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime inputTime = LocalTime.parse(scanner.nextLine(), dateTimeFormatter);

        return currentDateTime.withHour(inputTime.getHour()).withMinute(inputTime.getMinute());
    }
}
