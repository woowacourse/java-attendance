package controller.commands;

import controller.AttendanceController;
import domain.CrewGroup;

public class AttendanceChangeCommand implements Command {

    private final AttendanceController attendanceController;

    public AttendanceChangeCommand(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute(CrewGroup crewGroup) {
        attendanceController.changeAttendance(crewGroup);
    }
}
