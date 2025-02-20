package controller;

import domain.AttendanceBook;
import domain.CsvReader;
import domain.Parser;
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
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final CsvReader csvFileReader;
    private final OutputView outputView;
    private final InputView inputView;

    public AttendanceController(CsvReader csvFileReader, OutputView outputView, InputView inputView) {
        this.csvFileReader = csvFileReader;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void start() {
        String dataPath = "src/main/resources/attendances.csv";
        List<String> fileData = csvFileReader.readCsv(dataPath);

        List<String> removedData = Parser.parse(fileData);
        List<List<String>> seperatedData = Parser.parseName(removedData);

        AttendanceBook attendanceBook = new AttendanceBook();

        for (List<String> data : seperatedData) {
            String name = data.getFirst();
            Map<LocalDate, LocalTime> dateAndTime = Parser.parseDate(data.getLast());
            attendanceBook.initialize(name, dateAndTime);
        }

        while (true) {

            outputView.displayPrompt(); // 기능 선택창
            UserInput selection = retryUntilValid(this::getUserInput);

            try {
                if (selection == UserInput.CHECK_ATTENDANCE) {
                    checkAttendance(attendanceBook);
                }
                if (selection == UserInput.MODIFY_ATTENDANCE) {
                    modifyAttendance(attendanceBook);
                }
                if (selection == UserInput.TOTAL_RECORDS_BY_CREW) {
                    getTotalRecordsByCrew(attendanceBook);
                }
                if (selection == UserInput.CHECK_PENALTY) {
                    outputView.displayPenaltyCrew(attendanceBook.checkPenaltyCrew());
                }
                if (selection == UserInput.QUIT) {
                    break;
                }

            } catch (IllegalArgumentException e) {
                outputView.displayErrorMessage(e.getMessage());
            }
        }
    }

    private void getTotalRecordsByCrew(AttendanceBook attendanceBook) {
        String name = retryUntilValid(() -> askNameToCheckAttendance(attendanceBook));

        List<AttendanceRecordResponse> records = attendanceBook.getCrewByName(name).getAttendanceRecords();
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
            throw new IllegalArgumentException("[ERROR] 날짜(일) 입력이 올바르지 않습니다.");
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