package attendance;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceFileReader;
import attendance.domain.Nickname;
import attendance.domain.SystemDateTime;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.InputValidationException;
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
        optionMenu.put("1", this::processAttendance);
        optionMenu.put("2", this::processModify);
        optionMenu.put("3", this::processCrewStatistics);
        optionMenu.put("4", this::processSanctionsLevels);
    }

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        attendanceBook = readFile();
        process();
    }

    private AttendanceBook readFile() {
        try {
            var attendanceReader = new AttendanceFileReader(ATTENDANCE_CSV, systemDateTime);
            return attendanceReader.load();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void process() {
        try {
            handleAttendanceMethod();
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            handleAttendanceMethod();
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

    private void processAttendance() {
        outputView.printRequestNickName();
        var nickname = requestNickname();
        outputView.printRequestAttendanceTime();
        var time = handleInput(inputView::requestTime);
        var date = systemDateTime.nowDate();
        registerAttendance(date, time, nickname);
    }

    private void registerAttendance(LocalDate date, LocalTime time, Nickname nickname) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        attendanceBook.attendance(nickname, dateTime);
        String state = attendanceBook.getConvertedAttendanceState(nickname, date);
        outputView.printAttendance(dateTime, state);
    }

    private void processModify() {
        outputView.printRequestNickNameForModify();
        var nickname = requestNickname();
        outputView.printRequestDateForModify();
        var date = requestDate();
        outputView.printRequestTimeForModify();
        var time = handleInput(inputView::requestTime);

        modifyAttendance(nickname, date, time);
        outputView.flushStringBuilder();
    }

    private void modifyAttendance(Nickname nickname, LocalDate date, LocalTime time) {
        var dateTime = LocalDateTime.of(date, time);
        try {
            var oldAttendance = attendanceBook.getAttendance(nickname, date);
            outputView.appendOldAttendance(oldAttendance.dateTime(), oldAttendance.getConvertedStatus());
        } catch (AttendanceArgumentException e) {
            outputView.appendAbsenceAttendance(date);
        }
        attendanceBook.attendance(nickname, dateTime);
        var newAttendance = attendanceBook.getAttendance(nickname, date);
        outputView.appendNewAttendance(time, newAttendance.getConvertedStatus());
    }

    private void processCrewStatistics() {

    }

    private void processSanctionsLevels() {

    }

    private Nickname requestNickname() {
        return handleInput(() -> {
            var nickname = inputView.inputString();
            return new Nickname(nickname);
        });
    }

    private LocalDate requestDate() {
        var input = handleInput(inputView::requestDate);
        return systemDateTime.nowDate().withDayOfMonth(input);
    }

    private <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (AttendanceArgumentException | InputValidationException e) {
            outputView.printError(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
