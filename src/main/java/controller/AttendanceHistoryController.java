package controller;

import domain.*;
import exception.CustomException;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.util.List;

public class AttendanceHistoryController implements Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewAttendanceStorage crewAttendanceStorage;

    public AttendanceHistoryController(
            InputView inputView,
            OutputView outputView,
            CrewAttendanceStorage crewAttendanceStorage
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewAttendanceStorage = crewAttendanceStorage;
    }

    @Override
    public void run() {
        String name = inputView.readName();
        LocalDate endDate = CustomDate.now();
        LocalDate startDate = endDate.withDayOfMonth(1);
        try {
            List<Attendance> attendances = crewAttendanceStorage.findAttendanceByDateRange(name, startDate, endDate);
            AttendanceStatistic statistic = crewAttendanceStorage.findStatisticByDateRange(name, startDate, endDate);

            outputView.printAttendances(name, attendances);
            outputView.printAttendanceCounts(
                    statistic.getAttendanceCount(), statistic.getLateCount(), statistic.getAbsenceCount()
            );
            outputView.printRiskStatus(statistic.getExpulsionRiskStatus());
        } catch (CustomException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }
}
