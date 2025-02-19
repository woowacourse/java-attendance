package view;

public class OutputView {

    public static void printTodayAttendance(final int todayMonth, final int todayDay, final String todayDayOfWeek,
                                            final String schoolStartTime, final String attendanceResult) {
        System.out.printf("%d월 %02d일 %s %s (%s)",
                todayMonth, todayDay, todayDayOfWeek, schoolStartTime, attendanceResult);
    }
}
