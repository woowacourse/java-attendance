package presentation;

import domain.AttendanceBook;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import presentation.command.AttendCommand;
import presentation.command.CrewInfoCommand;
import presentation.command.EditCommand;
import presentation.command.WarningInfoCommand;
import view.AttendanceFileReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final LocalDate START_DATE = LocalDate.of(2024, 12, 2);

    private final AttendanceFileReader attendanceFileReader;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(AttendanceFileReader fileReader, InputView inputView, OutputView outputView) {
        this.attendanceFileReader = fileReader;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        AttendanceBook attendanceBook = initAttendanceBook();
        Map<String, Consumer<AttendanceBook>> commands = initCommand();
        String inputCommand = "";

        while (!inputCommand.equalsIgnoreCase("Q")) {
            inputCommand = processCommand(inputCommand, commands, attendanceBook);
        }

        inputView.close();
    }

    private String processCommand(String inputCommand, Map<String, Consumer<AttendanceBook>> commands,
                                  AttendanceBook attendanceBook) {
        try {
            inputCommand = inputView.inputCommand(LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일")));
            Consumer<AttendanceBook> command = commands.get(inputCommand);

            if (command == null) {
                throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
            }
            command.accept(attendanceBook);
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
        return inputCommand;
    }

    private Map<String, Consumer<AttendanceBook>> initCommand() {
        Map<String, Consumer<AttendanceBook>> commands = new HashMap<>();
        commands.put("1", new AttendCommand());
        commands.put("2", new EditCommand());
        commands.put("3", new CrewInfoCommand());
        commands.put("4", new WarningInfoCommand());
        return commands;
    }

    private AttendanceBook initAttendanceBook() {
        Map<String, List<String>> crewsInfo = attendanceFileReader.getInfo();
        return new AttendanceBook(crewsInfo.keySet().stream().toList());
    }
}
