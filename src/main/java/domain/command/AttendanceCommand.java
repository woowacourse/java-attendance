package domain.command;

import java.util.HashMap;
import java.util.Map;

public enum AttendanceCommand {

    ATTEND("1"),
    UPDATE("2"),
    DISPLAY("3"),
    EXPULSION("4"),

    QUIT("Q"),
    ERROR("");

    private static final Map<String, AttendanceCommand> COMMAND_CACHE = new HashMap<>();

    static {
        for (AttendanceCommand command : values()) {
            COMMAND_CACHE.put(command.key, command);
        }
    }

    private final String key;

    AttendanceCommand(String key) {
        this.key = key;
    }

    public static AttendanceCommand from(String key) {
        return COMMAND_CACHE.getOrDefault(key.toUpperCase(), ERROR);
    }
}
