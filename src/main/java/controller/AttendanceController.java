package controller;

import domain.Attendance;
import exception.CrewNotExistException;
import exception.DuplicateAttendanceException;
import service.AttendanceCheckService;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceCheckService attendanceCheckService;

    public AttendanceController(
            InputView inputView,
            OutputView outputView,
            AttendanceCheckService attendanceCheckService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceCheckService = attendanceCheckService;
    }

    public void checkAttendance() {
        String name = inputView.readName();
        String timeInput = inputView.readTime();
        LocalDate now = LocalDateTime.now().toLocalDate();
        String[] minuteAndHour = timeInput.split(":");
        LocalDateTime time = LocalDateTime.of(
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                Integer.parseInt(minuteAndHour[0]),
                Integer.parseInt(minuteAndHour[1])
        );
        registerAttendance(name, time);
    }

    private void registerAttendance(String name, LocalDateTime time) {
        try {
            Attendance attendance = attendanceCheckService.register(name, time);//출석등록
            outputView.printAttendanceResult(attendance);
        } catch (DuplicateAttendanceException e) {
            // TODO: 에러 잘 뜨는지 보기
            outputView.recommendModifyFunction(e.getMessage());
        } catch (CrewNotExistException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }
}
