package attendance;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceFileReader;
import attendance.domain.SystemDateTime;
import attendance.exception.AttendanceArgumentException;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {
    private static final SystemDateTime systemDateTime = new AttendanceDateTime();
    private static final Map<String, Runnable> optionMenu = new HashMap<>();
    private static final String ATTENDANCE_CSV = "attendances.csv";
    private static final String WRONG_INPUT = "잘못된 입력값입니다.";
    private static final String INPUT_SYSTEM_OUT = "Q";

    private final InputView inputView;
    private final OutputView outputView;
    private AttendanceBook attendanceBook;

    {
        optionMenu.put("1", this::registerAttendance);
        optionMenu.put("2", this::modifyAttendance);
        optionMenu.put("3", this::generateCrewStatistics);
        optionMenu.put("4", this::generateSanctionsLevelStatistics);
    }

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        attendanceBook = readFile();
        handleAttendanceMethod();
    }

    private AttendanceBook readFile() {
        try {
            var attendanceReader = new AttendanceFileReader(ATTENDANCE_CSV, systemDateTime);
            return attendanceReader.load();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAttendanceMethod() {
        Stream.generate(() -> {
                outputView.printRequestMessage(systemDateTime.now());
                outputView.printMethod();
                return handleInput(inputView::inputString);
            })
            .takeWhile(option -> !option.equals(INPUT_SYSTEM_OUT))
            .forEach(this::processOption);
    }

    private void processOption(String option) {
        Runnable runnable = optionMenu.getOrDefault(option, null);
        if (runnable == null) {
            throw new AttendanceArgumentException(WRONG_INPUT);
        }
        runnable.run();
    }

    private void registerAttendance() {
    }

    private void modifyAttendance() {

    }

    private void generateCrewStatistics() {

    }

    private void generateSanctionsLevelStatistics() {

    }

    private <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
