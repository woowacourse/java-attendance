package controller;

import domain.Attendance;
import domain.AttendanceCustomDate;
import domain.AttendanceStatus;
import domain.CrewStatus;
import service.AttendanceHistoryService;
import service.dto.AttendanceHistoryResponse;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AttendanceHistoryController implements Controller {
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
        LocalDate nowDate = AttendanceCustomDate.now().toLocalDate();
        Map<LocalDate, Attendance> histories = attendanceHistoryService.getHistoriesOf(name, nowDate.withDayOfMonth(1), nowDate);
        Map<AttendanceStatus, Integer> attendanceResult = attendanceHistoryService
                .getAttendanceResultOf(name, nowDate.withDayOfMonth(1), nowDate);
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, nowDate.withDayOfMonth(1), nowDate);
        outputView.printHistoryResult(name, histories, attendanceResult, crewStatus);
    }
}
