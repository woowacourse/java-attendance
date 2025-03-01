package attendance.controller;

import static attendance.view.Command.ATTENDANCE;

import attendance.AttendanceBookInitializer;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.view.Command;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        Command command = inputView.inputCommand(today);
        if (command == ATTENDANCE) {
            attend(today, attendanceBook);
        }
    }

    public void attend(LocalDate attendanceDate, AttendanceBook attendanceBook) {
        String nickname = inputView.inputNickname();
        LocalTime attendanceTime = inputView.inputAttendanceTime();
        
        LocalDateTime attendanceDateTime = LocalDateTime.of(attendanceDate, attendanceTime);
        attendanceBook.attend(nickname, attendanceDateTime);

        outputView.printAttendance(new Attendance(nickname, LocalDateTime.of(attendanceDate, attendanceTime)));
    }
}
