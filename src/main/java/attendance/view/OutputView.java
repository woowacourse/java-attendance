package attendance.view;

import attendance.model.Attendance;
import attendance.model.Crew;
import attendance.model.Status;

public class OutputView {
    private static final String ATTENDANCE_STRING = "%d월 %02d일 %s요일 %s (%s)";
    private static final String CREW_ATTENDANCE_HISTORY_STRING = "이번 달 %s의 출석 기록입니다.";
    private static final String CREW_STATISTICS_STRING = """
            출석: %d회
            지각: %d회
            결석: %d회
            """;
    private static final String CREW_STATUS_STRING = "%s 대상자입니다.";

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println("[ERROR] : " + e.getMessage());
    }

    public void printAttendMessage(Attendance todayAttendance) {
        int month = Integer.parseInt(todayAttendance.getMonth());
        int date = Integer.parseInt(todayAttendance.getDateOfMonth());
        String day = todayAttendance.getDayOfMonth();
        String time = todayAttendance.getTime();
        String type = todayAttendance.getType().toString();

        System.out.println(String.format(ATTENDANCE_STRING, month, date, day, time, type));
        System.out.println();
    }

    public void printCrewStatistic(Crew crew) {
        System.out.println(String.format(CREW_ATTENDANCE_HISTORY_STRING, crew.getName()));

        for (Attendance attendance : crew.getAttendanceHistory()) {
            int month = Integer.parseInt(attendance.getMonth());
            int date = Integer.parseInt(attendance.getDateOfMonth());
            String day = attendance.getDayOfMonth();
            String time = attendance.getTime();
            String type = attendance.getType().toString();

            if (time.equals("00:00")) {
                time = "--:--";
            }

            System.out.println(String.format(ATTENDANCE_STRING, month, date, day, time, type));
        }

        System.out.println();
        System.out.println(String.format(CREW_STATISTICS_STRING, crew.getPresentCount(), crew.getLateCount(),
                crew.getAbsentCount()));

        if (crew.getStatus() != Status.NONE) {
            System.out.println(String.format(CREW_STATUS_STRING, crew.getStatus().toString()));
        }

        System.out.println();
    }
}
