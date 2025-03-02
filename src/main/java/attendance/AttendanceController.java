package attendance;

import attendance.domain.SystemDateTime;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {
    private InputView inputView;
    private OutputView outputView;
    private SystemDateTime systemDateTime;

    public AttendanceController(InputView inputView, OutputView outputView, SystemDateTime systemDateTime) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.systemDateTime = systemDateTime;
    }

    public void run() {
    }
}
