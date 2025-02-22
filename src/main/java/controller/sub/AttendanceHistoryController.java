package controller.sub;

import exception.handler.ExceptionHandler;
import controller.sub.parent.SubController;
import domain.crew.Crew;
import domain.date.CustomDate;
import domain.attendance.AttendanceStatus;
import domain.crew.CrewStatus;
import repository.AttendanceRepository;
import service.AttendanceHistoryService;
import service.dto.AttendanceHistoryResponse;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AttendanceHistoryController implements SubController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceHistoryService attendanceHistoryService;
    private final AttendanceRepository attendanceRepository;

    public AttendanceHistoryController(InputView inputView, OutputView outputView,
                                       AttendanceHistoryService attendanceHistoryService,
                                       AttendanceRepository attendanceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceHistoryService = attendanceHistoryService;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public void run() {
        Crew crew = readCrew();
        LocalDate nowDate = CustomDate.now().toLocalDate(); //TODO : now
        List<AttendanceHistoryResponse> histories = attendanceHistoryService.getHistoriesOf(crew, nowDate);
        Map<AttendanceStatus, Integer> attendanceResult = attendanceHistoryService.getAttendanceResultOf(crew, nowDate);
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(crew, nowDate);
        outputView.printHistoryResult(crew, histories, attendanceResult, crewStatus);
    }

    private Crew readCrew() {
        return ExceptionHandler.retryIfIllegalArgumentAndReturn(() -> {
            String name = inputView.readName();
            return attendanceRepository.findCrewByName(name);
        });
    }
}
