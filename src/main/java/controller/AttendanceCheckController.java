package controller;

import domain.AttendanceCustomDate;
import exception.CrewNotExistException;
import exception.DuplicateAttendanceException;
import service.AttendanceCheckService;
import service.dto.AttendanceRegisterResponse;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceCheckController implements Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceCheckService attendanceCheckService;

    public AttendanceCheckController(
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
        LocalDateTime dateTime = LocalDateTime.of(
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                Integer.parseInt(minuteAndHour[0]),
                Integer.parseInt(minuteAndHour[1])
        );
        registerAttendance(name, dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    private void registerAttendance(String name, LocalDate date, LocalTime time) {
        try {
            AttendanceRegisterResponse response = attendanceCheckService.register(name, date, time);//출석등록
            outputView.printAttendanceResult(response);
        } catch (DuplicateAttendanceException e) {
            // TODO: 에러 잘 뜨는지 보기
            outputView.recommendModifyFunction(e.getMessage());
        } catch (CrewNotExistException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }
}
