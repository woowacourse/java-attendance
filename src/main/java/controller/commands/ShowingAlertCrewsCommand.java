package controller.commands;

import controller.AttendanceController;
import domain.CrewGroup;

public class ShowingAlertCrewsCommand implements Command {
    private final AttendanceController attendanceController;

    public ShowingAlertCrewsCommand(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute(CrewGroup crewGroup) {
        attendanceController.showAlertCrews(crewGroup);
    }
}
