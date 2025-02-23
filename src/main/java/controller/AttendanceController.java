package controller;

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
            outputView.displayFunctionSelectionPrompt(); // 선택 가능한 기능 안내
            FunctionSelection selection = retryUntilValid(this::getFunctionInput);

            try {
                checkAttendance(selection, attendanceBook);
                modifyAttendance(selection, attendanceBook);
                checkAttendanceRecord(selection, attendanceBook);
                CheckPenaltyCrew(selection, attendanceBook);
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

    // 기능 선택
    private FunctionSelection getFunctionInput() {
        return FunctionSelection.getFunctionByInput(inputView.getUserSelection());
    }

    // 기능 1. 출석 확인
    private void checkAttendance(FunctionSelection selection, AttendanceBook attendanceBook) {
        if (selection == FunctionSelection.CHECK_ATTENDANCE) {
            String name = retryUntilValid(() -> askName(attendanceBook));

            LocalTime parsedTime = retryUntilValid(() -> askTimeToCheckAttendance(attendanceBook));

            outputView.displayCheckAttendanceResult(
                    attendanceBook.checkAttendance(name, Map.of(LocalDate.now(), parsedTime)));
        }
    }

    // 기능 2. 출석 수정
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

    // 기능 3. 크루별 출석 기록 확인
    private void checkAttendanceRecord(FunctionSelection selection, AttendanceBook attendanceBook) {
        if (selection == FunctionSelection.CHECK_ATTENDANCE_RECORD) {
            String name = retryUntilValid(() -> askName(attendanceBook));

            List<AttendanceRecordResponse> records = attendanceBook.checkAttendanceHistoryByCrew(name);
            TotalRecordsResponse totalRecord = attendanceBook.checkAttendanceCountByCrew(records);
            PenaltyStatus penalty = PenaltyStatus.getByPenaltyCount(attendanceBook.calculatePenaltyCount(totalRecord));
            outputView.displayAttendanceRecordByName(name, records, totalRecord, penalty.getMessage());
        }
    }

    // 기능 4. 제적 위험자 확인
    private void CheckPenaltyCrew(FunctionSelection selection, AttendanceBook attendanceBook) {
        if (selection == FunctionSelection.CHECK_PENALTY_CREWS) {
            outputView.displayPenaltyCrew(attendanceBook.checkPenaltyCrew());
        }
    }

    // 기능별 필요한 데이터를 입력
    private String askName(AttendanceBook attendanceBook) {
        String name = inputView.askName();
        attendanceBook.validateNameAlreadyExists(name);
        return name;
    }

    private LocalTime askTimeToCheckAttendance(AttendanceBook attendanceBook) {
        String time = inputView.askTime();
        LocalTime parsedTime = LocalTime.parse(time);
        attendanceBook.validateIsInOperationHour(parsedTime);
        return parsedTime;
    }

    private LocalDate askDayToModify(AttendanceBook attendanceBook, String name) {
        try {
            LocalDate modifiedDay = LocalDate.now().withDayOfMonth(inputView.askDayForModify().getDayOfMonth());
            attendanceBook.validateRecordNotExistsByCrewName(name, modifiedDay);
            return modifiedDay;
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 날짜(일) 입력이 올바르지 않습니다.");
        }
    }

    private LocalTime askTimeToModify(AttendanceBook attendanceBook) {
        LocalTime modifiedTime = inputView.askTimeForModify();
        attendanceBook.validateIsInOperationHour(modifiedTime);
        return modifiedTime;
    }

    private String askNameToModify(AttendanceBook attendanceBook) {
        String name = inputView.askNameForModify();
        attendanceBook.validateNameAlreadyExists(name);
        return name;
    }

    // 재입력 메서드
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