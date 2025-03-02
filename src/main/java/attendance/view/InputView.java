package attendance.view;

import attendance.controller.AttendanceCommand;
import attendance.domain.LocalDateProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class InputView {
    private static final String COMMAND_MESSAGE_HEADER_FORMAT = "오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.\n";
    private static final String COMMAND_PROMPT_FORMAT = "%s. %s\n";
    private static final String NICK_NAME_PROMPT = "닉네임을 입력해 주세요.\n";
    private static final String ENTER_ATTENDANCE_TIME_PROMPT = "등교 시간을 입력해 주세요.\n";
    private static final String MODIFY_NICK_NAME_PROMPT = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.\n";
    private static final String MODIFY_DATE_PROMPT = "수정하려는 날짜(일)를 입력해 주세요.\n";
    private static final String MODIFY_TIME_PROMPT = "언제로 변경하겠습니까?\n";

    private final LocalDateProvider dateProvider;
    private final BufferedReader bufferedReader;


    public InputView(LocalDateProvider dateProvider) {
        this.dateProvider = dateProvider;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String inputCommand() {
        System.out.print(createCommandMessage());
        return readLine();
    }

    private String createCommandMessage() {
        LocalDate now = dateProvider.now();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(COMMAND_MESSAGE_HEADER_FORMAT, now.getMonthValue(), now.getDayOfMonth(),
                getDisplayName(now)));
        Arrays.stream(AttendanceCommand.values())
                .forEach(command -> {
                    builder.append(
                            String.format(COMMAND_PROMPT_FORMAT, command.getCommand(), command.getDescription()));
                });
        return builder.toString();
    }

    private static String getDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
    }

    public String inputCrewName() {
        System.out.print(NICK_NAME_PROMPT);
        return readLine();
    }

    public String inputModifyCrewName() {
        System.out.println(MODIFY_NICK_NAME_PROMPT);
        return readLine();
    }

    public LocalTime inputEnterTime() {
        try {
            System.out.print(ENTER_ATTENDANCE_TIME_PROMPT);
            return parseTime();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputEnterTime();
        }
    }

    public LocalTime inputModifyTime() {
        try {
            System.out.print(MODIFY_TIME_PROMPT);
            return parseTime();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputEnterTime();
        }
    }

    private LocalTime parseTime() {
        try {
            String inputTime = readLine();
            return LocalTime.parse(inputTime);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 올바르지 않습니다. HH:mm 형식으로 입력해주세요.\n");
        }
    }

    public LocalDate inputModifyDate() {
        try {
            System.out.println(MODIFY_DATE_PROMPT);
            LocalDate now = dateProvider.now();
            return LocalDate.of(now.getYear(), now.getMonthValue(), parseInt());
        } catch (DateTimeException e) {
            System.out.println("[ERROR] 날짜가 올바르지 않습니다. 다시 입력해 주세요.");
            return inputModifyDate();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputModifyDate();
        }
    }

    public Integer parseInt() {
        try {
            String inputTime = readLine();
            return Integer.parseInt(inputTime);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 입력이 올바르지 않습니다. 날짜(일)만 입력해주세요.\n");
        }
    }

    private String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입력 중 오류가 발생했습니다.", e);
        }
    }

    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException("입력 스트림을 닫는데 실패했습니다.", e);
        }
    }

}
