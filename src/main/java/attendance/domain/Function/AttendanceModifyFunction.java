package attendance.domain.Function;

import attendance.controller.AttendanceController;

public class AttendanceModifyFunction implements AttendanceFunction {

    private final AttendanceController attendanceController;

    public AttendanceModifyFunction(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public boolean execute() {
        attendanceController.attendanceModifyFunction();
        return false;
    }
}
