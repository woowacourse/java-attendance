package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceHistory;
import attendance.domain.HistoryStatistic;
import attendance.domain.SystemDateTime;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;
import attendance.utility.CsvReader;
import attendance.utility.DateTimeParser;
import attendance.view.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    private static final SystemDateTime systemDateTime = new AttendanceDateTime();
    private static final Map<String, Runnable> optionMenu = new HashMap<>();
    private static final String WRONG_INPUT = "잘못된 입력입니다.";
    private static final String FILE = "/attendances.csv";

    private static InputView inputView;
    private static OutputView outputView;
    private static AttendanceBook attendanceBook;

    static {
        optionMenu.put("1", Application::registerAttendance);
        optionMenu.put("2", Application::modifyAttendance);
        optionMenu.put("3", Application::checkAttendanceStatisticsByCrew);
        optionMenu.put("4", Application::checkSanctionStatistic);
    }

    public static void main(String[] args) {
        initialize();
        processAttendanceMenu();
    }

    private static void initialize() {
        try {
            inputView = new InputView();
            outputView = new OutputView();
            attendanceBook = generateAttendanceBook();
        } catch (AttendanceFileException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static AttendanceBook generateAttendanceBook() throws AttendanceFileException {
        var repository = new CsvReader(FILE);
        var lines = repository.getLines();
        return AttendanceBook.of(lines, systemDateTime);
    }

    public static void processAttendanceMenu() {
        try {
            handleAttendanceCommands();
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            processAttendanceMenu();
        }
    }

    private static void handleAttendanceCommands() {
        Stream.generate(() -> {
                outputView.printRequestMessage(systemDateTime.now());
                outputView.printMethod();
                return requestInputString();
            })
            .takeWhile(option -> !option.equals("Q"))
            .forEach(Application::run);
    }

    private static void run(String option) {
        Runnable runnable = optionMenu.getOrDefault(option, null);
        if (runnable == null) {
            throw new AttendanceArgumentException(WRONG_INPUT);
        }
        runnable.run();
    }

    private static void registerAttendance() {
        outputView.printRequestNickname();
        String nickname = requestInputString();
        try {
            requestRegister(nickname);

        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void requestRegister(String nickname) {
        var attendances = attendanceBook.getAttendances(nickname);
        LocalDateTime dateTime = systemDateTime.now();
        var newAttendance = Attendance.of(dateTime, systemDateTime);
        attendances.validateDuplicate(dateTime.toLocalDate());
        attendances.add(newAttendance);
        outputView.printlnRegister(newAttendance.dateTime(), newAttendance.attendanceStatus());
    }

    private static void modifyAttendance() {
        String nickname = requestInputStringForModify();
        var dateTime = requestLocalDateTime();
        try {
            requestModify(nickname, dateTime);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void requestModify(String nickname, LocalDateTime dateTime) {
        var attendances = attendanceBook.getAttendances(nickname);
        Attendance newAttendance = Attendance.of(dateTime, systemDateTime);
        Optional<Attendance> oldAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());
        attendances.add(newAttendance);
        if (oldAttendance.isPresent()) {
            outputView.printlnModifyOldAttendance(oldAttendance.get(), newAttendance);
            return;
        }
        outputView.printlnModifyNewAttendance(newAttendance);
    }

    private static void checkAttendanceStatisticsByCrew() {
        outputView.printRequestNickname();
        String nickname = requestInputString();
        try {
            requestStatistic(nickname);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void requestStatistic(String nickname) {
        outputView.printlnInitializeHistory(nickname);
        var attendances = attendanceBook.getAttendances(nickname);
        Map<LocalDate, Attendance> attendancesRecord = attendances.getAttendances();
        var attendanceHistory = AttendanceHistory.of(attendancesRecord, systemDateTime.extractWorkingDays());
        outputView.printlnHistory(attendanceHistory.history());

        var statusStatistic = new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname);
        outputView.printlnHistoryStatistic(statusStatistic.statistic());

        outputView.printJudgedSanctionLevel(statusStatistic.judgeSanctionLevel());
    }

    private static void checkSanctionStatistic() {
        outputView.printlnInitializeSanctionStatistic();
        List<HistoryStatistic> historyStatistics = new ArrayList<>();
        for (String nickname : attendanceBook.getNicknameSet()) {
            var attendances = attendanceBook.getAttendances(nickname);
            var attendanceHistory = AttendanceHistory.of(attendances.getAttendances(),
                systemDateTime.extractWorkingDays());
            historyStatistics.add(new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname));
        }
        Collections.sort(historyStatistics);
        outputView.printlnSanctionStatistic(historyStatistics);
    }

    private static String requestInputStringForModify() {
        outputView.printRequestNicknameForModify();
        return requestInputString();
    }

    private static String requestInputString() {
        return handleInput(() -> {
            String nickname = inputView.input();
            InputValidator.validateIsEmpty(nickname);
            return nickname;
        });
    }

    private static LocalDate requestDate() {
        outputView.printRequestDate();
        return handleInput(() -> {
            String input = inputView.input();
            InputValidator.validateIsEmpty(input);
            int parsedInt = InputValidator.validateInputTypeInteger(input);
            LocalDateTime current = systemDateTime.now().withDayOfMonth(parsedInt);
            return current.toLocalDate();
        });
    }

    private static LocalTime requestTime() {
        outputView.printRequestTime();
        return handleInput(() -> {
            String input = inputView.input();
            InputValidator.validateIsEmpty(input);
            return DateTimeParser.parseToTime(input);
        });
    }

    private static LocalDateTime requestLocalDateTime() {
        LocalDate date = requestDate();
        LocalTime time = requestTime();
        return LocalDateTime.of(date, time);
    }

    private static <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
