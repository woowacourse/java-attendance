package controller;

import static util.loader.FileLoader.loadCSV;

import domain.AttendanceBook;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBook attendanceBook;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;

        this.attendanceBook = new AttendanceBook();
        attendanceBook.initializeCrewRecords(loadCSV("src/main/resources/attendances.csv"));
    }

    public void selectFunction() {

    }
}
