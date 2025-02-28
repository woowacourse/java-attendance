package attendance.view.input;

import attendance.controller.Command;
import attendance.view.KoreaDayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class InputView {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final Scanner SCANNER = new Scanner(System.in);

    public Command inputCommand(final LocalDate nowDate) {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n", formatLocalDate(nowDate));
        Arrays.stream(Command.values())
                .map(Command::getMenu)
                .forEach(System.out::println);
        return Command.fromOption(SCANNER.nextLine());
    }

    public String inputCrewNickName() {
        System.out.println("닉네임을 입력해주세요.");
        return SCANNER.nextLine();
    }

    public LocalTime inputAttendanceTime() {
        try {
            System.out.println("등교 시간을 입력해주세요.");
            return LocalTime.parse(SCANNER.nextLine(), dateTimeFormatter);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 형식이 아닙니다.");
        }
    }

    public String inputUpdateCrewNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해주세요.");
        return SCANNER.nextLine();
    }

    public Date inputUpdateAttendanceDate() {
        System.out.println("수정하려는 날짜(일)를 입력해주세요.");
        return new Date(Integer.parseInt(SCANNER.nextLine()));
    }

    public LocalTime inputUpdateAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return LocalTime.parse(SCANNER.nextLine(), dateTimeFormatter);
    }

    private String formatLocalDate(final LocalDate date) {
        return String.format("%d월 %d일 %s",
                date.getMonthValue(),
                date.getDayOfMonth(),
                KoreaDayOfWeek.fromDayOfWeek(date.getDayOfWeek()).getName()
        );
    }
}
