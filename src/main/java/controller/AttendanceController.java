package controller;

import static domain.Feature.ATTENDANCE_CHECK;
import static domain.Feature.ATTENDANCE_EDIT;
import static domain.Feature.CREW_RECORDS_CHECK;
import static domain.Feature.EXPELLED_WARNING_CHECK;
import static util.loader.FileLoader.loadCSV;

import domain.AttendanceBook;
import domain.Feature;
import java.util.Map;
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
        handleException(() -> executeFeature());
    }

    protected void attendanceCheck() {
        // TODO: 출석 확인 구현
        System.out.println("출석 확인 기능");
    }

    protected void attendanceEdit() {
        // TODO: 출석 수정 구현
        System.out.println("출석 수정 기능");
    }

    protected void crewRecordsCheck() {
        // TODO: 크루별 출석 기록 확인 구현
        System.out.println("크루별 출석 기록 확인 기능");
    }

    protected void expelledWarningCheck() {
        // TODO: 제적 위험자 확인 구현
        System.out.println("제적 위험자 확인 기능");
    }

    protected Runnable selectFeature(String featureNumber) {
        Map<Feature, Runnable> features = Map.of(
            ATTENDANCE_CHECK, this::attendanceCheck,
            ATTENDANCE_EDIT, this::attendanceEdit,
            CREW_RECORDS_CHECK, this::crewRecordsCheck,
            EXPELLED_WARNING_CHECK, this::expelledWarningCheck
        );

        Feature.validateProvided(featureNumber);
        return features.get(Feature.of(featureNumber));
    }

    private void executeFeature() {
        String featureNumber = inputView.readFeature();
        while (!Feature.isExit(featureNumber)) {
            Runnable action = selectFeature(featureNumber);
            action.run();
            featureNumber = inputView.readFeature();
        }
    }

    private void handleException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            start();
        }
    }
}