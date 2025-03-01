package attendance.view;

import attendance.controller.AttendanceCommand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class InputView {
    private static final String COMMAND_MESSAGE_HEADER_FORMAT = "오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.\n";
    private static final String COMMAND_PROMPT_FORMAT = "%s. %s\n";
    private static final String NICK_NAME_PROMPT = "닉네임을 입력해 주세요.\n";


    private final BufferedReader bufferedReader;


    public InputView() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String inputCommand() {
        System.out.print(createCommandMessage());
        return readLine();
    }

    private String createCommandMessage() {
        LocalDate now = LocalDate.now();
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
