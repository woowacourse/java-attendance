package config;

import controller.facade.MainController;
import controller.sub.AttendanceRegisterController;
import controller.sub.AttendanceHistoryController;
import controller.sub.AttendanceModifyController;
import controller.sub.DisenrollmentCheckController;
import controller.sub.StoreController;
import controller.sub.parent.SubController;
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

    private SubController getHistoryController() {
        return new AttendanceHistoryController(
                getInputView(),
                getOutputView(),
                new AttendanceHistoryService(getAttendanceRepository()),
                getAttendanceRepository()
        );
    }

    private SubController getDisenrollmentCheckController() {
        return new DisenrollmentCheckController(
                getOutputView(),
                new DisenrollmentCheckService(getAttendanceRepository())
        );
    }

    private SubController getModifyController() {
        return new AttendanceModifyController(
                getInputView(),
                getOutputView(),
                new AttendanceModifyService(getAttendanceRepository()),
                getAttendanceRepository()
        );
    }

    private SubController getAttendanceStoreController() {
        return new StoreController(
                new AttendanceStoreService(getAttendanceRepository())
        );
    }

    public SubController getAttendanceController() {
        return new AttendanceRegisterController(
                getInputView(),
                getOutputView(),
                new AttendanceRegisterService(getAttendanceRepository()),
                getAttendanceRepository()
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
