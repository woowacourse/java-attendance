package controller;

import domain.AttendanceBook;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import controller.command.AttendCommand;
import controller.command.CrewInfoCommand;
import controller.command.EditCommand;
import controller.command.WarningInfoCommand;
import java.util.stream.Collectors;
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
            // todo: 오늘 날짜 출력하는 부분 책임 분리
            inputCommand = inputView.inputCommand(LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일")));
            Consumer<AttendanceBook> command = commands.get(inputCommand);
            validate(command);

            command.accept(attendanceBook);
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
        return inputCommand;
    }

    private void validate(Consumer<AttendanceBook> command) {
        if (command == null) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
        }
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Map<String, List<LocalDateTime>> parsedCrewsInfo = crewsInfo.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(dateTime -> LocalDateTime.parse(dateTime, formatter))
                                .toList()
                ));
        return new AttendanceBook(parsedCrewsInfo);
    }
}
