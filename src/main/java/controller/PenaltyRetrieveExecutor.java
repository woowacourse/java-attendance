package controller;

import domain.Attendance;
import domain.AttendanceManager;
import domain.AttendanceRecords;
import domain.CrewAttendance;
import domain.CrewPenaltyManager;
import domain.PenaltyTargetComparator;
import domain.vo.PenaltyTarget;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import util.ApplicationDate;
import view.ConsoleView;

public class PenaltyRetrieveExecutor implements MenuExecutor {

    private final ConsoleView consoleView;
    private final ApplicationDate applicationDate;
    private final AttendanceManager attendanceManager;
    private final CrewPenaltyManager crewPenaltyManager;

    public PenaltyRetrieveExecutor(
            ConsoleView consoleView,
            ApplicationDate applicationDate,
            AttendanceManager attendanceManager,
            CrewPenaltyManager crewPenaltyManager
    ) {
        this.consoleView = consoleView;
        this.applicationDate = applicationDate;
        this.attendanceManager = attendanceManager;
        this.crewPenaltyManager = crewPenaltyManager;
    }

    @Override
    public void execute() {
        LocalDate date = applicationDate.getApplicationDate();
        List<CrewAttendance> crewAttendances = attendanceManager.crewAttendanceBook().crewAttendances();
        List<PenaltyTarget> penaltyTargets = crewAttendances.stream()
                .map(crewAttendance -> {
                    String crewName = crewAttendance.crewName();
                    AttendanceRecords attendanceRecords = attendanceManager.retrieveFilledAttendanceUntilDate(crewName, date);
                    return crewPenaltyManager.retrieveAllPenaltyTarget(crewName, attendanceRecords);
                })
                .filter(penaltyTarget -> !penaltyTarget.isNone())
                .sorted(new PenaltyTargetComparator())
                .toList();
        consoleView.printPenaltyTargets(penaltyTargets);
    }
}
