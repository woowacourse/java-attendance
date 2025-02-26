package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import attendance.domain.AttendanceFileReader;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendanceManager.ModifyManager;
import attendance.domain.attendanceManager.RegisterManager;
import attendance.domain.attendanceManager.SanctionManager;
import attendance.domain.attendanceManager.StatisticManger;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;
import attendance.utility.StringUtility;
import attendance.utility.dateTimeUtility;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    private static final Map<String, AttendanceOperation> optionMenu = new HashMap<>();
    private static final String WRONG_INPUT = "잘못된 입력입니다.";
    private static final String FILE = "/attendances.csv";

    private static InputView inputView;
    private static OutputView outputView;

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
            AttendanceBook attendanceBook = getAttendanceBook();
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
        RegisterManager attendanceManager = new RegisterManager(attendanceBook);
        outputView.printRequestNickname();
        String nickname = requestInputString();
        try {
            attendanceManager.manage(nickname, SystemDateConfig.NOW_DATETIME);
            String result = attendanceManager.getResult();
            outputView.println(result);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void modifyAttendance(AttendanceBook attendanceBook) {
        ModifyManager attendanceManager = new ModifyManager(attendanceBook);
        String nickname = requestInputStringForModify();
        var dateTime = requestLocalDateTime();
        try {
            attendanceManager.manage(nickname, dateTime);
            String result = attendanceManager.getResult();
            outputView.println(result);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void checkAttendanceStatisticsByCrew(AttendanceBook attendanceBook) {
        StatisticManger attendanceManager = new StatisticManger(attendanceBook);
        outputView.printRequestNickname();
        String nickname = requestInputString();
        try {
            attendanceManager.manage(nickname);
            String result = attendanceManager.getResult();
            outputView.println(result);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void checkSanctionStatistic(AttendanceBook attendanceBook) {
        SanctionManager attendanceManager = new SanctionManager(attendanceBook);
        attendanceManager.manage();
        String result = attendanceManager.getResult();
        outputView.println(result);
    }

    private static String requestInputString() {
        return handleInput(() -> {
            String nickname = inputView.input();
            StringUtility.validateIsEmpty(nickname);
            return nickname;
        });
    }

    private static String requestInputStringForModify() {
        outputView.printRequestNicknameForModify();
        return requestInputString();
    }

    private static LocalDate requestDate() {
        outputView.printRequestDate();
        return handleInput(() -> {
            String input = inputView.input();
            StringUtility.validateIsEmpty(input);
            String date = SystemDateConfig.YEAR_MONTH + input;
            return dateTimeUtility.parseToDate(date);
        });
    }

    private static LocalTime requestTime() {
        outputView.printRequestTime();
        return handleInput(() -> {
            String input = inputView.input();
            StringUtility.validateIsEmpty(input);
            return dateTimeUtility.parseToTime(input);
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
