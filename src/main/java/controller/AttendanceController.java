package controller;

import controller.command.Command;
import domain.AttendanceBook;
import domain.AttendanceBookFactory;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import controller.command.AttendCommand;
import controller.command.CrewInfoCommand;
import controller.command.EditCommand;
import controller.command.WarningInfoCommand;
import view.AttendanceFileReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    public static final LocalDate START_DATE = LocalDate.of(2024, 12, 2);
    public static final LocalDate END_DATE = LocalDate.now();

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        AttendanceBookFactory attendanceBookFactory = new AttendanceBookFactory(new AttendanceFileReader());
        AttendanceBook attendanceBook = attendanceBookFactory.generate();
        Map<String, Consumer<AttendanceBook>> commands = initCommand();
        String inputCommand = "";

        while (!inputCommand.equalsIgnoreCase(Command.EXIT_COMMAND.getCommand())) {
            inputCommand = processCommand(inputCommand, commands, attendanceBook);
        }
        inputView.close();
    }

    private Map<String, Consumer<AttendanceBook>> initCommand() {
        Map<String, Consumer<AttendanceBook>> commands = new HashMap<>();
        commands.put(Command.ATTEND_COMMAND.getCommand(), new AttendCommand(inputView, outputView));
        commands.put(Command.EDIT_COMMAND.getCommand(), new EditCommand(inputView, outputView));
        commands.put(Command.CREW_INFO_COMMAND.getCommand(), new CrewInfoCommand(inputView, outputView));
        commands.put(Command.WARNING_CREW_COMMAND.getCommand(), new WarningInfoCommand(outputView));
        return commands;
    }

    private String processCommand(String inputCommand, Map<String, Consumer<AttendanceBook>> commands,
                                  AttendanceBook attendanceBook) {
        try {
            inputCommand = inputView.inputCommand(DateTimeConverter.convertLocalDateToString(END_DATE));
            if (inputCommand.equalsIgnoreCase(Command.EXIT_COMMAND.getCommand())) {
                return inputCommand;
            }
            executeCommand(inputCommand, commands, attendanceBook);
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
        return inputCommand;
    }

    private void executeCommand(String inputCommand, Map<String, Consumer<AttendanceBook>> commands,
                                AttendanceBook attendanceBook) {
        Consumer<AttendanceBook> command = commands.get(inputCommand);
        validate(command);
        command.accept(attendanceBook);
    }

    private void validate(Consumer<AttendanceBook> command) {
        if (command == null) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
        }
    }
}
