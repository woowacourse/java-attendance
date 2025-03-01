package attendance.controller;

import static attendance.view.Command.ATTENDANCE;
import static attendance.view.Command.from;

import attendance.AttendanceBookInitializer;
import attendance.domain.AttendanceBook;
import attendance.view.Command;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(LocalDate today) {
        AttendanceBook attendanceBook = new AttendanceBookInitializer().Initialize();

        Command command = from(inputView.inputCommand(today));
        if (command == ATTENDANCE) {
            attend(today, attendanceBook);
        }
    }

    public void attend(LocalDate today, AttendanceBook attendanceBook) {
        String nickname = inputView.inputNickname();
        LocalTime attendanceTime = inputView.inputAttendanceTime();
    }
}
