package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import attendance.domain.AttendanceFileReader;
import attendance.domain.HistoryStatistic;
import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendance.AttendanceHistory;
import attendance.domain.attendance.Attendances;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;
import attendance.utility.DateTimeParser;
import attendance.view.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    private static final Map<String, AttendanceOperation> optionMenu = new HashMap<>();
    private static final String WRONG_INPUT = "잘못된 입력입니다.";
    private static final String FILE = "/attendances.csv";

    private static InputView inputView;
    private static OutputView outputView;
    private static AttendanceBook attendanceBook;

    @FunctionalInterface
    interface AttendanceOperation {
        void run(AttendanceBook attendanceBook);
    }

    static {
        optionMenu.put("1", Application::registerAttendance);
        optionMenu.put("2", Application::modifyAttendance);
        optionMenu.put("3", Application::checkAttendanceStatisticsByCrew);
        optionMenu.put("4", Application::checkSanctionStatistic);
    }

    public static void main(String[] args) {
        try {
            inputView = new InputView();
            outputView = new OutputView();
            attendanceBook = getAttendanceBook();
            processAttendanceMenu(attendanceBook);
        } catch (AttendanceFileException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static AttendanceBook getAttendanceBook() throws AttendanceFileException {
        var repository = AttendanceFileReader.from(FILE);
        var lines = repository.getLines();
        return AttendanceBook.from(lines);
    }

    public static void processAttendanceMenu(AttendanceBook attendanceBook) {
        try {
            handleAttendanceCommands(attendanceBook);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            processAttendanceMenu(attendanceBook);
        }
    }

    private static void handleAttendanceCommands(AttendanceBook attendanceBook) {
        Stream.generate(() -> {
                outputView.printRequestMessage(SystemDateConfig.NOW_DATE);
                outputView.printMethod();
                return requestInputString();
            })
            .takeWhile(option -> !option.equals("Q"))
            .forEach(option -> run(option, attendanceBook));
    }

    private static void run(String option, AttendanceBook attendanceBook) {
        AttendanceOperation runnable = optionMenu.getOrDefault(option, null);
        if (runnable == null) {
            throw new AttendanceArgumentException(WRONG_INPUT);
        }
        runnable.run(attendanceBook);
    }

    private static void registerAttendance(AttendanceBook attendanceBook) {
        outputView.printRequestNickname();
        String nickname = requestInputString();
        try {
            requestRegister(nickname);
            outputView.println("");
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void requestRegister(String nickname) {
        var attendances = attendanceBook.getAttendances(nickname);
        LocalDateTime dateTime = SystemDateConfig.NOW_DATETIME;
        var newAttendance = Attendance.from(dateTime);
        attendances.validateDuplicate(dateTime.toLocalDate());
        attendances.add(newAttendance);
    }

    private static void modifyAttendance(AttendanceBook attendanceBook) {
        String nickname = requestInputStringForModify();
        var dateTime = requestLocalDateTime();
        try {
            requestModify(nickname, dateTime);
            outputView.println("");
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void requestModify(String nickname, LocalDateTime dateTime) {
        Attendances attendances = attendanceBook.getAttendances(nickname);
        Attendance newAttendance = Attendance.from(dateTime);
        Optional<Attendance> oldAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());
        oldAttendance.ifPresent(attendance -> attendances.remove(attendance.getDate()));
        attendances.add(newAttendance);
    }

    private static void checkAttendanceStatisticsByCrew(AttendanceBook attendanceBook) {
        outputView.printRequestNickname();
        String nickname = requestInputString();
        try {
            requestStatistic(nickname);
            outputView.println("result");
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void requestStatistic(String nickname) {
        var attendances = attendanceBook.getAttendances(nickname);
        Map<LocalDate, Attendance> attendancesRecord = attendances.getAttendances();
        //1. attendances에서 출석한 날과 아닌 날을 포함한 리스트가 반환되어야 함.
        var attendanceHistory = AttendanceHistory.from(attendancesRecord);
        //2. 출석 / 지각 / 결석 계산
        var statusStatistic = new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname);
        //3. 면담 대상자 계산
        statusStatistic.judgeSanctionLevel();
    }

    private static void checkSanctionStatistic(AttendanceBook attendanceBook) {
        // StatusStatistic statistics = new StatusStatistic();
        // attendanceBook.updateStatusStatistics(statistics);

        // 정렬 Collections.sort(statusStatistics);
        outputView.println("result");
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
            String date = SystemDateConfig.YEAR_MONTH + input;
            return DateTimeParser.parseToDate(date);
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
