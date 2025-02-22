package controller;

import static domain.AttendanceBook.fromAttendanceRecords;

import domain.AttendanceBook;
import domain.PenaltyStatus;
import dto.AttendanceRecordResponse;
import dto.ModifyAttendanceResponse;
import dto.TotalRecordsResponse;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import service.CsvReader;
import service.FunctionSelection;
import service.InputParser;
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
        AttendanceBook attendanceBook = init();

        while (true) {
            outputView.displayFunctionSelectionPrompt(); // 기능 선택창
            FunctionSelection selection = retryUntilValid(this::getFunctionInput);

            try {
                checkAttendance(selection, attendanceBook);
                modifyAttendance(selection, attendanceBook);
                checkAttendanceRecord(selection, attendanceBook);

                if (selection == FunctionSelection.CHECK_PENALTY_CREWS) {
                    outputView.displayPenaltyCrew(attendanceBook.checkPenaltyCrew());
                }
                if (selection == FunctionSelection.QUIT) {
                    System.exit(1);
                }
            } catch (IllegalArgumentException e) {
                outputView.displayErrorMessage(e.getMessage());
            }
        }
    }

    // csv 파일을 바탕으로 출석부 생성
    private AttendanceBook init() {
        String dataPath = "src/main/resources/attendances.csv";
        List<String> existedCrewRecords = csvFileReader.readCsv(dataPath);
        existedCrewRecords.removeFirst();

        AttendanceBook attendanceBook = new AttendanceBook();

        for (String existedCrewRecord : existedCrewRecords) {
            String name = InputParser.parseRecordToNameAndDate(existedCrewRecord).getFirst();
            String rawDateTime = InputParser.parseRecordToNameAndDate(existedCrewRecord).getLast();
            Map<LocalDate, LocalTime> dateAndTime = InputParser.parseDateToDayAndTime(rawDateTime);
            attendanceBook.initialize(name, dateAndTime);
        }

        return attendanceBook;
    }

    private FunctionSelection getFunctionInput() {
        return FunctionSelection.getFunctionByInput(inputView.getUserSelection());
    }

    private void checkAttendance(FunctionSelection selection, AttendanceBook attendanceBook) {
        if (selection == FunctionSelection.CHECK_ATTENDANCE) {
            String name = retryUntilValid(() -> askNameToCheckAttendance(attendanceBook));

            LocalTime parsedTime = retryUntilValid(() -> getTime(attendanceBook));

            outputView.displayCheckAttendanceResult(
                    attendanceBook.checkAttendance(name, Map.of(LocalDate.now(), parsedTime)));
        }
    }

    private void modifyAttendance(FunctionSelection selection, AttendanceBook attendanceBook) {
        if (selection == FunctionSelection.MODIFY_ATTENDANCE) {
            String name = retryUntilValid(() -> askNameToModify(attendanceBook));

            LocalDate modifiedDay = retryUntilValid(() -> askDayToModify(attendanceBook, name));

            LocalTime modifiedTime = retryUntilValid(() -> askTimeToModify(attendanceBook));

            ModifyAttendanceResponse response = attendanceBook.modifyAttendance(name,
                    Map.of(modifiedDay, modifiedTime));
            outputView.displayModifyAttendanceResult(response);
        }
    }

    private void checkAttendanceRecord(FunctionSelection selection, AttendanceBook attendanceBook) {
        if (selection == FunctionSelection.CHECK_ATTENDANCE_RECORD) {
            String name = retryUntilValid(() -> askNameToCheckAttendance(attendanceBook));

            List<AttendanceRecordResponse> records = attendanceBook.getCrewByName(name).getAttendanceRecords();
            TotalRecordsResponse totalRecord = fromAttendanceRecords(records);
            PenaltyStatus penalty = PenaltyStatus.getByPenaltyCount(attendanceBook.getPenaltyCount(totalRecord));
            outputView.displayAttendanceRecordByName(name, records, totalRecord, penalty.getMessage());
        }
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