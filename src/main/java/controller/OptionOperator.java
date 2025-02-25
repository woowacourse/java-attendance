package controller;

import domain.AttendanceBook;
import java.time.LocalTime;
import view.InputView;
import view.OutputView;

public interface OptionOperator {

    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    void process(AttendanceBook attendanceBook, LocalTime nowTime);
}
