package controller.sub;

import controller.sub.parent.SubController;
import domain.date.CustomDate;
import domain.attendance.AttendanceStatus;
import domain.crew.CrewStatus;
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

    public AttendanceHistoryController(
            InputView inputView,
            OutputView outputView,
            AttendanceHistoryService attendanceHistoryService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceHistoryService = attendanceHistoryService;
    }

    @Override
    public void run() {
        String name = inputView.readName();
        LocalDate nowDate = CustomDate.now().toLocalDate();
        List<AttendanceHistoryResponse> histories = attendanceHistoryService.getHistoriesOf(name, nowDate);
        Map<AttendanceStatus, Integer> attendanceResult = attendanceHistoryService.getAttendanceResultOf(name, nowDate);
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, nowDate);
        outputView.printHistoryResult(name, histories, attendanceResult, crewStatus);
    }
}
