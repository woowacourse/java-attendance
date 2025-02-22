package attendance.domain.Function;

import attendance.controller.AttendanceController;

public class AttendanceCheckFunction implements AttendanceFunction {

    private final AttendanceController attendanceController;

    public AttendanceCheckFunction(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public boolean execute() {
        attendanceController.attendanceCheckFunction();
        return false;
    }
}
