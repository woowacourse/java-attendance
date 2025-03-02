package controller.commands;

import controller.AttendanceController;
import domain.CrewGroup;

public class AttendanceMarkCommand implements Command {
    private final AttendanceController attendanceController;

    public AttendanceMarkCommand(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute(CrewGroup crewGroup) {
        attendanceController.markAttendance(crewGroup);
    }
}
