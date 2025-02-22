package controller.sub;

import exception.InvalidTimeException;
import exception.handler.ExceptionHandler;
import controller.sub.parent.SubController;
import domain.attendance.Attendance;
import domain.crew.Crew;
import domain.date.CustomDate;
import exception.CannotRegisterAttendanceException;
import exception.DuplicateAttendanceException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import repository.AttendanceRepository;
import service.AttendanceRegisterService;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceRegisterController implements SubController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceRegisterService attendanceCheckService;
    private final AttendanceRepository attendanceRepository;

    public AttendanceRegisterController(InputView inputView,
                                        OutputView outputView,
                                        AttendanceRegisterService attendanceCheckService,
                                        AttendanceRepository attendanceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceCheckService = attendanceCheckService;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public void run() {
        LocalDateTime now = CustomDate.now(); //NOW에 대한 책임소재도 Config에 넣으면 좋을듯!!!
        CustomDate.throwIfHolidy(now);
        Crew crew = readCrewName();
        LocalTime timeInput = readTime();
        LocalDateTime time = LocalDateTime.of( //TODO: 한곳에서 생성
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                timeInput.getHour(),
                timeInput.getMinute()
        );
        registerAttendance(crew, time);
    }

    private Crew readCrewName() {
        return ExceptionHandler.retryIfIllegalArgumentAndReturn(() -> {
            String name = inputView.readName();
            return attendanceRepository.findCrewByName(name);
        });
    }

    private LocalTime readTime() {
        return ExceptionHandler.retryIfIllegalArgumentAndReturn(() -> {
            try {
                return inputView.readTime();
            } catch (DateTimeParseException e) {
                throw new InvalidTimeException();
            }
        });
    }

    private void registerAttendance(Crew crew, LocalDateTime time) {
        try {
            Attendance attendance = attendanceCheckService.register(crew, time);
            outputView.printAttendanceResult(attendance);
        } catch (DuplicateAttendanceException e) {
            outputView.recommendModifyFunction(e.getMessage());
        }
    }
}
