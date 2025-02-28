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
        handleException(()
            -> attendanceBook.initializeCrewRecords(loadCSV("src/main/resources/attendances.csv")));
    }

    public void start() {

    }

    protected void attendanceCheck() {
        // TODO: 출석 확인 구현
    }

    protected void attendanceEdit() {
        // TODO: 출석 수정 구현
    }

    protected void crewRecordsCheck() {
        // TODO: 크루별 출석 기록 확인 구현
    }

    protected void expelledWarningCheck() {
        // TODO: 제적 위험자 확인 구현
    }

    protected Runnable selectFunction(String functionNumber) {
        // TODO: 함수 선택 구현
        return null;
    }

    private void handleException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}