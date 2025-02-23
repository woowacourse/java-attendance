package controller;

import domain.AttendanceBook;
import domain.Crew;
import domain.ErrorCode;
import domain.PenaltyStatus;
import domain.UserInput;
import dto.AttendanceRecordResponse;
import dto.ModifyAttendanceResponse;
import dto.TotalRecordsResponse;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import util.FileReader;
import util.Parser;
import util.Parser.NameParsedData;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final FileReader fileReader;
    private final OutputView outputView;
    private final InputView inputView;

    public AttendanceController(FileReader fileReader, OutputView outputView, InputView inputView) {
        this.fileReader = fileReader;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void start() {
        AttendanceBook attendanceBook = initializeAttendanceBook();

        while (true) {
            outputView.displayPrompt();
            UserInput selection = retryUntilValid(this::getUserInput);

            if (processUserSelection(selection, attendanceBook)) {
                break;
            }
        }
    }

    private AttendanceBook initializeAttendanceBook() {
        List<String> fileData = fileReader.readFile();
        List<String> removedData = Parser.parse(fileData);
        List<NameParsedData> separatedData = Parser.parseName(removedData);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initializeAttendanceBook(separatedData);
        return attendanceBook;
    }

    private boolean processUserSelection(UserInput selection, AttendanceBook attendanceBook) {
        try {
            Map<UserInput, Runnable> actions = Map.of(
                    UserInput.CHECK_ATTENDANCE, () -> checkAttendance(attendanceBook),
                    UserInput.MODIFY_ATTENDANCE, () -> modifyAttendance(attendanceBook),
                    UserInput.TOTAL_RECORDS_BY_CREW, () -> getTotalRecordsByCrew(attendanceBook),
                    UserInput.CHECK_PENALTY, () -> outputView.displayPenaltyCrew(attendanceBook.checkPenaltyCrew())
            );

            if (selection == UserInput.QUIT) {
                return true;
            }

            actions.getOrDefault(selection,
                    () -> outputView.displayErrorMessage(ErrorCode.INPUT_NOT_VALID.getMessage())).run();
        } catch (IllegalArgumentException e) {
            outputView.displayErrorMessage(e.getMessage());
        }
        return false;
    }

    private void getTotalRecordsByCrew(AttendanceBook attendanceBook) {
        String name = retryUntilValid(() -> askNameToCheckAttendance(attendanceBook));
        Crew foundCrew = attendanceBook.getCrewByName(name);
        List<AttendanceRecordResponse> records = foundCrew.getAttendanceRecords();
        TotalRecordsResponse totalRecord = TotalRecordsResponse.fromAttendanceRecords(records);
        PenaltyStatus penalty = PenaltyStatus.getByPenaltyCount(attendanceBook.getPenaltyCount(totalRecord));

        outputView.displayAttendanceRecordByName(name, records, totalRecord, penalty.getMessage());
    }

    private void modifyAttendance(AttendanceBook attendanceBook) {
        String name = retryUntilValid(() -> askNameToModify(attendanceBook));
        LocalDate modifiedDay = retryUntilValid(() -> askDayToModify(attendanceBook, name));
        LocalTime modifiedTime = retryUntilValid(() -> askTimeToModify(attendanceBook));
        ModifyAttendanceResponse response = attendanceBook.modifyAttendance(name,
                Map.of(modifiedDay, modifiedTime));
        outputView.displayModifyAttendanceResult(response);
    }

    private LocalTime askTimeToModify(AttendanceBook attendanceBook) {
        LocalTime modifiedTime = inputView.askTimeForModify();
        attendanceBook.validateIsInOperationHour(modifiedTime);
        return modifiedTime;
    }

    private LocalDate askDayToModify(AttendanceBook attendanceBook, String name) {
        try {
            LocalDate modifiedDay = LocalDate.now().withDayOfMonth(inputView.askDayForModify().getDayOfMonth());
            attendanceBook.validateDateAlreadyExistsByCrewName(name, modifiedDay);
            return modifiedDay;
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.DAY_INPUT_NOT_VALID.getMessage());
        }
    }

    private String askNameToModify(AttendanceBook attendanceBook) {
        String name = inputView.askNameForModify();
        attendanceBook.validateNameAlreadyExists(name);
        return name;
    }

    private void checkAttendance(AttendanceBook attendanceBook) {
        String name = retryUntilValid(() -> askNameToCheckAttendance(attendanceBook));
        LocalTime parsedTime = retryUntilValid(() -> getTime(attendanceBook));
        outputView.displayCheckAttendanceResult(
                attendanceBook.checkAttendance(name, Map.of(LocalDate.now(), parsedTime)));
    }

    private UserInput getUserInput() {
        return UserInput.getByInput(inputView.getUserSelection());
    }

    private LocalTime getTime(AttendanceBook attendanceBook) {
        String time = inputView.askTime();
        LocalTime parsedTime = LocalTime.parse(time);
        attendanceBook.validateIsInOperationHour(parsedTime);
        return parsedTime;
    }

    private String askNameToCheckAttendance(AttendanceBook attendanceBook) {
        String name = inputView.askName();
        attendanceBook.validateNameAlreadyExists(name);
        return name;
    }

    private <T> T retryUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.displayErrorMessage(e.getMessage());
            }
        }
    }
}
