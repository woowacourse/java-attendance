package controller.sub;

import domain.Attendance;
import domain.AttendanceCustomDate;
import exception.CrewNotExistException;
import exception.DuplicateAttendanceException;
import java.time.LocalTime;
import service.AttendanceRegisterService;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceRegisterController implements SubController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceRegisterService attendanceCheckService;

    public AttendanceRegisterController(
            InputView inputView,
            OutputView outputView,
            AttendanceRegisterService attendanceCheckService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceCheckService = attendanceCheckService;
    }

    @Override
    public void run() {
        String name = inputView.readName();
        LocalTime timeInput = inputView.readTime();
        LocalDate now = AttendanceCustomDate.now().toLocalDate();
        LocalDateTime time = LocalDateTime.of( //TODO: 한곳에서 생성
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                timeInput.getHour(),
                timeInput.getMinute()
        );
        registerAttendance(name, time);
    }

    private void registerAttendance(String name, LocalDateTime time) {
        try {
            Attendance attendance = attendanceCheckService.register(name, time);
            outputView.printAttendanceResult(attendance);
        } catch (DuplicateAttendanceException e) {
            outputView.recommendModifyFunction(e.getMessage());
        } catch (CrewNotExistException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }
}
