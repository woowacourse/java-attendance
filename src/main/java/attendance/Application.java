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
import attendance.domain.AttendanceStatus;
import attendance.domain.HistoryStatistic;
import attendance.domain.SanctionLevel;
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
        outputView.printlnRegister(newAttendance.getStatusString(), newAttendance.dateTime());
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
        oldAttendance.ifPresentOrElse(
            attendance -> outputView.appendModifiedOldAttendance(attendance.getStatusString(), attendance.dateTime()),
            () -> outputView.appendModifiedAbsence(dateTime)
        );
        outputView.printlnModify(newAttendance.getStatusString(), newAttendance.getTime());
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
        var attendanceHistory = getAttendanceHistory(nickname);
        displaySortedHistory(attendanceHistory);

        var statusStatistic = new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname);
        displayHistoryStatusStatistic(statusStatistic);

        SanctionLevel sanctionLevel = statusStatistic.judgeSanctionLevel();
        outputView.printJudgedSanctionLevel(sanctionLevel.getValues());
    }

    private static void displaySortedHistory(AttendanceHistory attendanceHistory) {
        List<LocalDate> sortedDates = attendanceHistory.getSortedHistoryKey();
        for (LocalDate sortedDate : sortedDates) {
            attendanceHistory.getAttendance(sortedDate)
                .ifPresentOrElse(
                    attendance -> outputView.appendAttendance(attendance.dateTime(), attendance.getStatusString()),
                    () -> outputView.appendAbsence(sortedDate)
                );
        }
        outputView.printStringBuilder();
    }

    private static void displayHistoryStatusStatistic(HistoryStatistic statusStatistic) {
        var keySet = statusStatistic.getStatusesReverseOrder();
        for (AttendanceStatus status : keySet) {
            int count = statusStatistic.getOrDefault(status, 0);
            outputView.appendStatisticToBuilder(status.getStatus(), count);
        }
        outputView.printStringBuilder();
    }

    private static AttendanceHistory getAttendanceHistory(String nickname) {
        var attendances = attendanceBook.getAttendances(nickname);
        Map<LocalDate, Attendance> attendancesRecord = attendances.getAttendances();
        return AttendanceHistory.of(attendancesRecord, systemDateTime.extractWorkingDays());
    }

    private static void checkSanctionStatistic() {
        outputView.printlnInitializeSanctionStatistic();
        List<HistoryStatistic> historyStatistics = getHistoryStatistics();
        Collections.sort(historyStatistics);
        displaySanctionStatistic(historyStatistics);
    }

    private static void displaySanctionStatistic(List<HistoryStatistic> historyStatistics) {
        for (HistoryStatistic historyStatistic : historyStatistics) {
            String nickname = historyStatistic.nickname();
            int absenceCount = historyStatistic.statistic().getOrDefault(AttendanceStatus.ABSENCE, 0);
            int LateCount = historyStatistic.statistic().getOrDefault(AttendanceStatus.LATE, 0);
            String sanctionLevel = historyStatistic.judgeSanctionLevel().getValues();
            outputView.appendSanctionStatistic(nickname, absenceCount, LateCount, sanctionLevel);
        }
        outputView.printStringBuilder();
    }

    private static List<HistoryStatistic> getHistoryStatistics() {
        List<HistoryStatistic> historyStatistics = new ArrayList<>();
        for (String nickname : attendanceBook.getNicknameSet()) {
            var attendances = attendanceBook.getAttendances(nickname);
            var attendanceHistory = AttendanceHistory.of(attendances.getAttendances(),
                systemDateTime.extractWorkingDays());
            historyStatistics.add(new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname));
        }
        return historyStatistics;
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
