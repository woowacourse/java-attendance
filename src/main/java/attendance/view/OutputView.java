package attendance.view;

import attendance.model.Attendance;

public class OutputView {
    private static final String ATTENDANCE_STRING = "%s월 %s일 %s요일 %s (%s)";

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println("[ERROR] : " + e.getMessage());
    }

    public void printAttendMessage(Attendance todayAttendance) {
        String month = todayAttendance.getMonth();
        String date = todayAttendance.getDateOfMonth();
        String day = todayAttendance.getDayOfMonth();
        String time = todayAttendance.getTime();
        String type = todayAttendance.getType().toString();

        System.out.println(String.format(ATTENDANCE_STRING, month, date, day, time, type));
    }

//    public void printCrewStatistic(Crew crew) {
//    }
}
