package domain;

public class MenuOption {
    private static final String ATTENDANCE_CHECK = "1";
    private static final String ATTENDANCE_MODIFY = "2";
    private static final String ATTENDANCE_HISTORY_PRINT = "3";
    private static final String PENALTY_PRINT = "4";
    private static final String QUIT = "[Qq]";

    public static boolean isCheckAttendance(String option) {
        return option.equals(ATTENDANCE_CHECK);
    }

    public static boolean isModifyAttendance(String option) {
        return option.equals(ATTENDANCE_MODIFY);
    }

    public static boolean isPrintAttendanceHistory(String option) {
        return option.equals(ATTENDANCE_HISTORY_PRINT);
    }

    public static boolean isPenaltyAttendance(String option) {
        return option.equals(PENALTY_PRINT);
    }

    public static boolean onRunning(String option) {
        return !option.matches(QUIT);
    }
}
