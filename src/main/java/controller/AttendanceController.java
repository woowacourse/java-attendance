package controller;

import domain.Attendance;
import java.time.LocalDate;
import util.RepeatExecutor;
import view.InputView;
import view.OutputView;

public interface AttendanceController {

    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    RepeatExecutor repeatExecutor = new RepeatExecutor(outputView);

    void process(Attendance attendance, LocalDate nowDate);
}
