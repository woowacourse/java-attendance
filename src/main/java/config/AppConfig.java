package config;

import controller.*;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;
import service.*;
import view.InputView;
import view.OutputView;

public class AppConfig {
    private AttendanceRepository attendanceRepository;
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
                new AttendanceHistoryService(getAttendanceRepository())
        );
    }

    private Controller getDisenrollmentCheckController() {
        return new DisenrollmentCheckController(
                getOutputView(),
                new DisenrollmentCheckService(getAttendanceRepository())
        );
    }

    private Controller getModifyController() {
        return new AttendanceModifyController(
                getInputView(),
                getOutputView(),
                new AttendanceModifyService(getAttendanceRepository())
        );
    }

    private Controller getAttendanceStoreController() {
        return new StoreController(
                new AttendanceStoreService(getAttendanceRepository())
        );
    }

    public Controller getAttendanceController() {
        return new AttendanceCheckController(
                getInputView(),
                getOutputView(),
                new AttendanceCheckService(getAttendanceRepository())
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

    private AttendanceRepository getAttendanceRepository() {
        if (attendanceRepository == null) {
            attendanceRepository = new AttendanceRepositoryImpl();
        }
        return attendanceRepository;
    }
}
