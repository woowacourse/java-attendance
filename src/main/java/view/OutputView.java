package view;

import domain.Attendance;
import domain.AttendanceState;
import java.time.LocalDate;
import java.time.LocalTime;
import util.DateTimeUtil;

public class OutputView {
    public static void printAttendanceCheck(LocalDate nowLocalDate, String localTime, AttendanceState state) {
        System.out.printf("12월 %02d일 %s %s (%s)\n", nowLocalDate.getDayOfMonth(),
                DateTimeUtil.getDayOfWeekBy(nowLocalDate), localTime, state.getState());
    }

    public static void printAttendanceUpdate(Attendance beforeAttendance, AttendanceState beforeState, String time,
                                             AttendanceState afterState) {
        System.out.printf("12월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                DateTimeUtil.getDateBy(beforeAttendance.getLocalDate()),
                DateTimeUtil.getDayOfWeekBy(beforeAttendance.getLocalDate()),
                formatTime(beforeAttendance.getLocalTime()),
                beforeState.getState(),
                time,
                afterState.getState()
        );
    }

    private static String formatTime(LocalTime beforeTime) {
        if (beforeTime.equals(LocalTime.of(0, 0))) {
            return "--:--";
        }
        return String.format("%02d:%02d", beforeTime.getHour(), beforeTime.getMinute());
    }
}
