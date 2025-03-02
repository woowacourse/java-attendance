package controller;

import domain.AttendanceStatistics;
import domain.Attendances;
import domain.CrewGroup;
import java.time.LocalDate;
import service.CrewLoader;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final LocalDate today = LocalDate.of(2024, 12, 14);
    private final InputView inputView;
    private final OutputView outputView;


    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.load(today);

        showCrewAttendanceLog(crewGroup);
    }

    public void showCrewAttendanceLog(CrewGroup crewGroup) {
        String name = inputView.insertName();
        Attendances attendances = crewGroup.getSpecificAttendances(name);
        AttendanceStatistics attendanceStatistics = new AttendanceStatistics(attendances.calculatePresent(),
                attendances.calculateLate(), attendances.calculateAbsent(), attendances.calucateAlertCode());

        outputView.printAllLog(name, attendances, attendanceStatistics);
    }
}
