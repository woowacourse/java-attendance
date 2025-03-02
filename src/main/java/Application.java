import static view.UserCommandType.ATTENDANCE_CHANGE;
import static view.UserCommandType.ATTENDANCE_MARK;
import static view.UserCommandType.QUIT;
import static view.UserCommandType.SHOWING_ALERT_CREWS;
import static view.UserCommandType.SHOWING_ATTENDANCE;

import controller.AttendanceController;
import controller.commands.AttendanceChangeCommand;
import controller.commands.AttendanceMarkCommand;
import controller.commands.QuitCommand;
import controller.commands.ShowingAlertCrewsCommand;
import controller.commands.ShowingAttendanceCommand;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        AttendanceController attendanceController = new AttendanceController(inputView, outputView);

        attendanceController.register(ATTENDANCE_MARK, new AttendanceMarkCommand(attendanceController));
        attendanceController.register(ATTENDANCE_CHANGE, new AttendanceChangeCommand(attendanceController));
        attendanceController.register(SHOWING_ATTENDANCE, new ShowingAttendanceCommand(attendanceController));
        attendanceController.register(SHOWING_ALERT_CREWS, new ShowingAlertCrewsCommand(attendanceController));
        attendanceController.register(QUIT, new QuitCommand());
      
        attendanceController.run();
    }
}
