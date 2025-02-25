package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import attendance.common.SystemDateConfig;
import attendance.common.exception.AttendanceArgumentException;
import attendance.common.exception.AttendanceFileException;
import attendance.common.utill.StringUtility;
import attendance.domain.AttendanceFileReader;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendanceManager.ModifyManager;
import attendance.domain.attendanceManager.RegisterManager;
import attendance.domain.attendanceManager.SanctionManager;
import attendance.domain.attendanceManager.StatisticManger;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    private static final String FILE = "/attendances.csv";
    private static final Map<String, AttendanceOperation> optionMenu = new HashMap<>();

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
            initialize();
            chooseMenu();
        } catch (AttendanceFileException e) {
            outputView.printError(e.getMessage());
        }
    }

    public static void initialize() {
        inputView = new InputView();
        outputView = new OutputView();
    }

    public static void chooseMenu() throws AttendanceFileException {
        AttendanceBook attendanceBook = getAttendanceBook();
        while (true) {
            outputView.printMethod();
            String option = requestNickname();
            if (option.equals("Q")) {
                break;
            }
            run(option, attendanceBook);
        }
    }

    private static AttendanceBook getAttendanceBook() throws AttendanceFileException {
        var repository = AttendanceFileReader.from(FILE);
        var lines = repository.getLines();
        return AttendanceBook.from(lines);
    }

    private static void run(String option, AttendanceBook attendanceBook) {
        AttendanceOperation runnable = optionMenu.getOrDefault(option, null);
        if (runnable == null) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        runnable.run(attendanceBook);
    }

    private static void registerAttendance(AttendanceBook attendanceBook) {
        RegisterManager attendanceManager = new RegisterManager(attendanceBook);

        String nickname = requestNickname();
        try {
            attendanceManager.manage(nickname, SystemDateConfig.SYSTEM_NOW_DATETIME);
            String result = attendanceManager.getResult();
            outputView.println(result);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private static void modifyAttendance(AttendanceBook attendanceBook) {
        ModifyManager attendanceManager = new ModifyManager(attendanceBook);
        String nickname = requestNickname();
        LocalDate date = requestDate();
        LocalTime time = requestTime();
        var dateTime = LocalDateTime.of(date, time);

        attendanceManager.manage(nickname, dateTime);
        String result = attendanceManager.getResult();
        outputView.println(result);

    }

    private static void checkAttendanceStatisticsByCrew(AttendanceBook attendanceBook) {
        StatisticManger attendanceManager = new StatisticManger(attendanceBook);
        String nickname = requestNickname();

        attendanceManager.manage(nickname);
        String result = attendanceManager.getResult();
        outputView.println(result);
    }

    private static void checkSanctionStatistic(AttendanceBook attendanceBook) {
        SanctionManager attendanceManager = new SanctionManager(attendanceBook);
        attendanceManager.manage();
        String result = attendanceManager.getResult();
        outputView.println(result);
    }

    private static String requestNickname() {
        return handleInput(() -> {
            String nickname = inputView.input();
            isEmptyForString(nickname);
            return nickname;
        });
    }

    private static LocalDate requestDate() {
        return handleInput(() -> {
            String input = inputView.input();
            isEmptyForString(input);
            int day = Integer.parseInt(input);
            return LocalDate.of(2024, Month.DECEMBER, day);
        });
    }

    private static LocalTime requestTime() {
        return handleInput(() -> {
            String Date = inputView.input();
            isEmptyForString(Date);
            return LocalTime.parse(Date);
        });
    }

    private static void isEmptyForString(String nickname) {
        if (StringUtility.isEmpty(nickname)) {
            throw new AttendanceArgumentException("공백 에러");
        }
    }

    private static <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            return handleInput(inputSupplier);
        }
    }

    private static <T> void handleManage(Supplier<T> inputSupplier) {
        try {
            inputSupplier.get();
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            handleManage(inputSupplier);
        }
    }
}
