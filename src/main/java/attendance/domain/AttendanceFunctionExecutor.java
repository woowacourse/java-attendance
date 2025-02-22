package attendance.domain;

import attendance.controller.AttendanceController;
import attendance.domain.Function.AttendanceCheckFunction;
import attendance.domain.Function.AttendanceFunction;
import attendance.domain.Function.AttendanceHistoryFunction;
import attendance.domain.Function.AttendanceModifyFunction;
import attendance.domain.Function.QuitFunction;
import attendance.domain.Function.RiskOfExpulsionFunction;
import java.util.Map;

public class AttendanceFunctionExecutor {

    private final AttendanceController attendanceController;
    private final Map<String, AttendanceFunction> functions;

    public AttendanceFunctionExecutor(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
        this.functions = initializeFunctions();
    }

    private Map<String, AttendanceFunction> initializeFunctions() {
        return Map.of(
                "1", new AttendanceCheckFunction(attendanceController),
                "2", new AttendanceModifyFunction(attendanceController),
                "3", new AttendanceHistoryFunction(attendanceController),
                "4", new RiskOfExpulsionFunction(attendanceController),
                "Q", new QuitFunction()
        );
    }

    public boolean execute(String functionValue) {
        try {
            return functions.getOrDefault(functionValue, () -> {
                throw new IllegalArgumentException("[ERROR] 올바른 기능을 입력해주세요.");
            }).execute();
        } catch (IllegalArgumentException e) {
            attendanceController.printErrorMessage(e.getMessage());
            return false;
        }
    }
}
