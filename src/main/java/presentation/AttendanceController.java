package presentation;

import domain.AttendanceBook;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import presentation.command.AttendCommand;
import presentation.command.CrewInfoCommand;
import presentation.command.EditCommand;
import presentation.command.WarningInfoCommand;
import view.InputView;

public class AttendanceController {
    private final Map<String, Consumer<AttendanceBook>> commands;

    public AttendanceController() {
        this.commands = new HashMap<>();
        commands.put("1", new AttendCommand());
        commands.put("2", new EditCommand());
        commands.put("3", new CrewInfoCommand());
        commands.put("4", new WarningInfoCommand());
    }

    public void run() {
        String inputCommand = "";
        AttendanceBook attendanceBook = initAttendanceBook();

        while (!inputCommand.equalsIgnoreCase("Q")) {
            inputCommand = InputView.inputCommand();
            Consumer<AttendanceBook> command = commands.get(inputCommand);

            if (command == null) {
                continue;
            }
            command.accept(attendanceBook);
        }
    }

    private AttendanceBook initAttendanceBook() {
        return new AttendanceBook(null);
    }
}
