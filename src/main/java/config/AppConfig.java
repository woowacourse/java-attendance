package config;

import controller.*;
import domain.CrewAttendances;
import service.*;
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
                new AttendanceHistoryService(getCrewAttendances())
        );
    }

    private Controller getDisenrollmentCheckController() {
        return new DisenrollmentCheckController(
                getOutputView(),
                new DisenrollmentCheckService(getCrewAttendances())
        );
    }

    private Controller getModifyController() {
        return new AttendanceModifyController(
                getInputView(),
                getOutputView(),
                new AttendanceModifyService(getCrewAttendances())
        );
    }

    private Controller getAttendanceStoreController() {
        return new StoreController(
                new AttendanceStoreService(getCrewAttendances())
        );
    }

    public Controller getAttendanceController() {
        return new AttendanceCheckController(
                getInputView(),
                getOutputView(),
                new AttendanceCheckService(getCrewAttendances())
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
