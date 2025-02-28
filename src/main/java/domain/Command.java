package domain;

import java.util.Arrays;
import java.util.List;

public enum Command {

    ATTEND_TODAY(List.of("1")),
    CHANGE_ATTENDANCE(List.of("2")),
    SHOW_CREW_ATTENDANCES(List.of("3")),
    SHOW_DISMISSAL_CREW(List.of("4")),
    Quit(List.of("Q"));

    private final List<String> commands;

    Command(List<String> commands) {
        this.commands = commands;
    }

    public static Command findCommand(String input) {
        System.out.println(input);
        return Arrays.stream(Command.values()).filter(command -> command.commands.contains(input)).findAny().orElseThrow(()->new IllegalArgumentException("없는 커맨드입니다."));
    }

    public List<String> getCommands() {
        return commands;
    }
}
