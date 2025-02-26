package config;

import controller.*;
import domain.AttendanceStoreManager;
import domain.CrewAttendances;
import view.InputView;
import view.OutputView;

public class AppConfig {
    private CrewAttendances crewAttendances;
    private InputView inputView;
    private OutputView outputView;

    public MainController getMainController() {
        return new MainController(
                getInputView(),
                getOutputView(),
                getAttendanceStoreController(),
                getAttendanceController(),
                getModifyController(),
                getHistoryController(),
                getDisenrollmentCheckController()
        );
    }

    private Controller getHistoryController() {
        return new AttendanceHistoryController(
                getInputView(),
                getOutputView(),
                getCrewAttendances()
        );
    }

    private Controller getDisenrollmentCheckController() {
        return new DisenrollmentCheckController(
                getOutputView(),
                getCrewAttendances()
        );
    }

    private Controller getModifyController() {
        return new AttendanceModifyController(
                getInputView(),
                getOutputView(),
                getCrewAttendances()
        );
    }

    private Controller getAttendanceStoreController() {
        return new StoreController(
                new AttendanceStoreManager(getCrewAttendances())
        );
    }

    public Controller getAttendanceController() {
        return new AttendanceCheckController(
                getInputView(),
                getOutputView(),
                getCrewAttendances()
        );
    }

    private InputView getInputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    private OutputView getOutputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

    private CrewAttendances getCrewAttendances() {
        if (crewAttendances == null) {
            crewAttendances = new CrewAttendances();
        }
        return crewAttendances;
    }
}
