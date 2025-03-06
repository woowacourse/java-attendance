package domain.command;

import domain.AttendanceBook;

import java.util.EnumMap;
import java.util.function.Consumer;

public class AttendanceCommandHandler {

    private final EnumMap<AttendanceCommand, Consumer<AttendanceBook>> menuActions;

    public AttendanceCommandHandler() {
        menuActions = new EnumMap<>(AttendanceCommand.class);
    }

    public void addAction(AttendanceCommand command, Consumer<AttendanceBook> action) {
        menuActions.put(command, action);
    }

    public void execute(AttendanceCommand command, AttendanceBook attendanceBook) {
        if (command.equals(AttendanceCommand.ERROR)) {
            throw new IllegalArgumentException("올바른 기능을 선택하세요");
        }
        menuActions.get(command).accept(attendanceBook);
    }
}
