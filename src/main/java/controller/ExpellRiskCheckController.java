package controller;

import domain.CrewAttendanceStorage;
import domain.CustomDate;
import domain.RiskCrewStatistics;
import view.OutputView;

import java.time.LocalDate;

public class ExpellRiskCheckController implements Controller{
    private final OutputView outputView;
    private final CrewAttendanceStorage crewAttendanceStorage;

    public ExpellRiskCheckController(
            OutputView outputView,
            CrewAttendanceStorage crewAttendanceStorage
    ) {
        this.outputView = outputView;
        this.crewAttendanceStorage = crewAttendanceStorage;
    }

    @Override
    public void run() {
        LocalDate endDate = CustomDate.now();
        LocalDate startDate = endDate.withDayOfMonth(1);

        RiskCrewStatistics statistics = crewAttendanceStorage.findRiskCrewStatistics(startDate, endDate);

        outputView.printRiskCrewStatistics(statistics);
    }
}
