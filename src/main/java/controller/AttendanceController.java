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
    private static final String ATTEND_COMMAND = "1";
    private static final String EDIT_COMMAND = "2";
    private static final String CREW_INFO_COMMAND = "3";
    private static final String WARNING_CREW_COMMAND = "4";
    private static final String EXIT_COMMAND = "Q";

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

        while (!inputCommand.equalsIgnoreCase(EXIT_COMMAND)) {
            inputCommand = processCommand(inputCommand, commands, attendanceBook);
        }
        inputView.close();
    }

    private String processCommand(String inputCommand, Map<String, Consumer<AttendanceBook>> commands,
                                  AttendanceBook attendanceBook) {
        try {
            // todo: 오늘 날짜 출력하는 부분 책임 분리
            inputCommand = inputView.inputCommand(LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일")));
            if (inputCommand.equalsIgnoreCase(EXIT_COMMAND)) {
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

    private Map<String, Consumer<AttendanceBook>> initCommand() {
        Map<String, Consumer<AttendanceBook>> commands = new HashMap<>();
        commands.put(ATTEND_COMMAND, new AttendCommand());
        commands.put(EDIT_COMMAND, new EditCommand());
        commands.put(CREW_INFO_COMMAND, new CrewInfoCommand());
        commands.put(WARNING_CREW_COMMAND, new WarningInfoCommand());
        return commands;
    }

    private AttendanceBook initAttendanceBook() {
        Map<String, List<String>> crewsInfo = attendanceFileReader.getInfo();

        // todo: 날짜파싱 책임 분리
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Map<String, List<LocalDateTime>> parsedCrewsInfo = parseCrewsDateTime(crewsInfo, formatter);
        return new AttendanceBook(parsedCrewsInfo);
    }

    private static Map<String, List<LocalDateTime>> parseCrewsDateTime(Map<String, List<String>> crewsInfo,
                                                                       DateTimeFormatter formatter) {
        return crewsInfo.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        crewEntry -> crewEntry.getValue().stream()
                                .map(dateTime -> LocalDateTime.parse(dateTime, formatter))
                                .toList()
                ));
    }
}
