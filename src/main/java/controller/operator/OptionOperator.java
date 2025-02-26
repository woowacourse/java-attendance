package controller.operator;

import domain.AttendanceBook;
import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public interface OptionOperator {

    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    void process(AttendanceBook attendanceBook, LocalDate attendanceDate);
}
