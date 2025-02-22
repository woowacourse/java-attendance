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

    private final static String CHECK_FUNCTION = "1";
    private final static String MODIFY_FUNCTION = "2";
    private final static String HISTORY_FUNCTION = "3";
    private final static String RISK_OF_EXPULSION_FUNCTION = "4";
    private final static String QUIT_FUNCTION = "Q";


    private final AttendanceController attendanceController;
    private final Map<String, AttendanceFunction> functions;

    public AttendanceFunctionExecutor(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
        this.functions = initializeFunctions();
    }

    private Map<String, AttendanceFunction> initializeFunctions() {
        return Map.of(
                CHECK_FUNCTION, new AttendanceCheckFunction(attendanceController),
                MODIFY_FUNCTION, new AttendanceModifyFunction(attendanceController),
                HISTORY_FUNCTION, new AttendanceHistoryFunction(attendanceController),
                RISK_OF_EXPULSION_FUNCTION, new RiskOfExpulsionFunction(attendanceController),
                QUIT_FUNCTION, new QuitFunction()
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
