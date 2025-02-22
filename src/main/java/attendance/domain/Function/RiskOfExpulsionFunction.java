package attendance.domain.Function;

import attendance.controller.AttendanceController;

public class RiskOfExpulsionFunction implements AttendanceFunction {

    private final AttendanceController attendanceController;

    public RiskOfExpulsionFunction(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }


    @Override
    public boolean execute() {
        attendanceController.crewAtRiskOfExpulsion();
        return false;
    }
}
