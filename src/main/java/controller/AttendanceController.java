package controller;

import domain.Attendance;
import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public interface AttendanceController {

    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    void process(Attendance attendance, LocalDate nowDate);
}
