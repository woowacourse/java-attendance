package controller;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import model.AttendanceBook;
import view.CommandInputView;

public class AttendanceController {

    private final CommandInputView commandInputView;
    private final Map<Command, BiConsumer<AttendanceBook, LocalDate>> commandByBiConsumer
            = new EnumMap<>(Command.class);

    public AttendanceController(
            final CommandInputView commandInputView,
            final List<BiConsumer<AttendanceBook, LocalDate>> consumers
    ) {
        this.commandInputView = commandInputView;
        commandByBiConsumer.putAll(Map.of(
                Command.CHECK_ATTENDANCE, consumers.removeFirst(),
                Command.MODIFY_ATTENDANCE, consumers.removeFirst(),
                Command.ATTENDANCE_HISTORY_BY_CREW, consumers.removeFirst(),
                Command.CHECK_DISMISSAL_CREW, consumers.removeFirst()
        ));
    }

    public void start(final AttendanceBook attendanceBook) {
        while (true) {
            LocalDate todayDate = getTodayDate();
            Command command = Command.from(commandInputView.getCommand(todayDate));
            if (command.equals(Command.QUIT)) {
                return;
            }
            commandByBiConsumer.get(command).accept(attendanceBook, todayDate);
        }
    }

    private LocalDate getTodayDate() {
        return LocalDate.of(2024, 12, 13);
    }
}
