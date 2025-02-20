package controller;

import domain.AttendanceBook;
import domain.CsvReader;
import domain.Parser;
import domain.UserInput;
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

        outputView.displayPrompt(); // 기능 선택창

        UserInput selection = UserInput.getByInput(inputView.getUserSelection());

        if (selection == UserInput.CHECK_ATTENDANCE) { // 출석 확인
            String name = inputView.askName();
            attendanceBook.validateNameAlreadyExists(name);

            String time = inputView.askTime();
            LocalTime parsedTime = LocalTime.parse(time);

            attendanceBook.validateIsInOperationHour(parsedTime);

            outputView.displayCheckAttendanceResult(
                    attendanceBook.checkAttendance(name, Map.of(LocalDate.now(), parsedTime)));
        }

        if (selection == UserInput.MODIFY_ATTENDANCE) { // 출석 확인
            String name = inputView.askNameForModify();

            String modifiedDay = inputView.askDayForModify();

            String modifiedTime = inputView.askTimeForModify();
        }

        inputView.askName();


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