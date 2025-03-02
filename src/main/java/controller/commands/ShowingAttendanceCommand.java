package controller.commands;

import controller.AttendanceController;
import domain.CrewGroup;

public class ShowingAttendanceCommand implements Command {

    private final AttendanceController attendanceController;

    public ShowingAttendanceCommand(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute(CrewGroup crewGroup) {
        attendanceController.showCrewAttendanceLog(crewGroup);
    }
}
