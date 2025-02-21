package controller.sub;

import domain.Attendance;
import domain.AttendanceCustomDate;
import exception.CrewNotExistException;
import exception.DuplicateAttendanceException;
import service.AttendanceCheckService;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceRegisterController implements SubController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceCheckService attendanceCheckService;

    public AttendanceRegisterController(
            InputView inputView,
            OutputView outputView,
            AttendanceCheckService attendanceCheckService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceCheckService = attendanceCheckService;
    }

    @Override
    public void run() {
        //TODO : 오늘이 출석 일자인지 확인하고 아니면 에러 날리기
        String name = inputView.readName();
        String timeInput = inputView.readTime();
        LocalDate now = AttendanceCustomDate.now().toLocalDate();
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
