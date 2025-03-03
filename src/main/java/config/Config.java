package config;

import controller.AttendanceController;
import view.InputView;
import view.OutputView;

public class Config {
    private static OutputView outputView;
    private static InputView inputView;
    private static AttendanceController attendanceController;

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            return new AttendanceController(outputView(), inputView());
        }
        return attendanceController;
    }

    private OutputView outputView() {
        if (outputView == null) {
            return new OutputView();
        }
        return outputView;
    }

    private InputView inputView() {
        if (inputView == null) {
            return new InputView();
        }
        return inputView;
    }
}
