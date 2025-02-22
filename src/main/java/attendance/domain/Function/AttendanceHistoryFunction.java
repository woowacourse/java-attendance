package attendance.domain.Function;

import attendance.controller.AttendanceController;

public class AttendanceHistoryFunction implements AttendanceFunction {

    private final AttendanceController attendanceController;

    public AttendanceHistoryFunction(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public boolean execute() {
        attendanceController.attendanceHistoryByNameFunction();
        return false;
    }
}
