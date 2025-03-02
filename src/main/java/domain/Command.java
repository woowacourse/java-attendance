package domain;

import controller.command.AttendanceCheck;
import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum Command {
    CHECK(AttendanceCheck::new),
    EDIT(null),
    VIEW_ATTENDANCE_BY_CREW(null),
    VIEW_AT_RISK_MEMBERS(null),
    QUIT(null);

    private final CommandMapper mapper;

    Command(final CommandMapper mapper) {
        this.mapper = mapper;
    }

    public static Command findByCommandNumber(final String inputCommandNumber) {
        try {
            final ResourceBundle resourceBundle = ResourceBundle.getBundle("command");
            final String inputCommand = resourceBundle.getString(inputCommandNumber);

            return Arrays.stream(Command.values())
                    .filter(command -> command.name().equals(inputCommand))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다. 1, 2, 3, 4, Q중에 선택해주세요."));
        } catch (final MissingResourceException e) {
            throw new IllegalArgumentException("존재하지 않는 명령어입니다. 1, 2, 3, 4, Q중에 선택해주세요.");
        }
    }

    public controller.command.AttendanceCommand getCommandInstance() {
        if (mapper == null) {
            return null;
        }
        return mapper.apply();
    }

    @FunctionalInterface
    public interface CommandMapper {
        controller.command.AttendanceCommand apply();
    }
}
