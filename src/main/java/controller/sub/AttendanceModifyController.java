package controller.sub;

import domain.date.CustomDate;
import exception.InvalidTimeException;
import exception.handler.ExceptionHandler;
import controller.sub.parent.SubController;
import domain.crew.Crew;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import repository.AttendanceRepository;
import service.AttendanceModifyService;
import service.dto.AttendanceModifyResponse;
import view.InputView;
import view.OutputView;

import java.time.LocalTime;

public class AttendanceModifyController implements SubController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceModifyService modifyService;
    private final AttendanceRepository attendanceRepository;

    public AttendanceModifyController(InputView inputView, OutputView outputView, AttendanceModifyService modifyService,
                                      AttendanceRepository attendanceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.modifyService = modifyService;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public void run() {
        Crew crew = readCrew();
        int modifyDate = readModifyDate();
        LocalTime modifyTime = readModifyTime();
        AttendanceModifyResponse response = modifyService.modify(
                crew,
                modifyDate,
                modifyTime.getHour(),
                modifyTime.getMinute()
        );
        outputView.printModifyResult(response);
    }

    private Crew readCrew() {
        return ExceptionHandler.retryIfIllegalArgumentAndReturn(() -> {
            String crewName = inputView.readName();
            return attendanceRepository.findCrewByName(crewName);
        });
    }

    private int readModifyDate() {
        return ExceptionHandler.retryIfIllegalArgumentAndReturn(() -> {
            int modifyDate = inputView.readModifyDate(); //TODO : 검증 추가
            CustomDate.throwIfHolidy(LocalDateTime.of(
                    CustomDate.YEAR, CustomDate.CUSTOM_MONTH.getValue(), modifyDate, 0, 0));
            return modifyDate;
        });
    }

    private LocalTime readModifyTime() {
        return ExceptionHandler.retryIfIllegalArgumentAndReturn(() -> {
            try {
                return inputView.readModifyTime();
            } catch (DateTimeParseException e) {
                throw new InvalidTimeException();
            }
        });
    }
}
