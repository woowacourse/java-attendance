package view;

import domain.Attendance;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import util.DateTimeUtil;

public class OutputView {
    public static void printAttendanceCheck(Attendance attendance) {
        System.out.printf("12월 %02d일 %s %s (%s)\n",
                DateTimeUtil.getDateBy(attendance.getLocalDate()),
                DateTimeUtil.getDayOfWeekBy(attendance.getLocalDate()),
                formatTime(attendance.getLocalTime()),
                attendance.getState().getState());
    }

    public static void printAttendanceUpdate(Attendance beforeAttendance, Attendance afterAttendance) {
        System.out.printf("12월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                DateTimeUtil.getDateBy(beforeAttendance.getLocalDate()),
                DateTimeUtil.getDayOfWeekBy(beforeAttendance.getLocalDate()),
                formatTime(beforeAttendance.getLocalTime()),
                beforeAttendance.getState().getState(),
                formatTime(afterAttendance.getLocalTime()),
                afterAttendance.getState().getState()
        );
    }

    private static String formatTime(LocalTime beforeTime) {
        if (beforeTime.equals(LocalTime.of(0, 0))) {
            return "--:--";
        }
        return String.format("%02d:%02d", beforeTime.getHour(), beforeTime.getMinute());
    }

    public static void printAttendanceHistory(List<Attendance> attendanceBookHistory) {
        sortByDate(attendanceBookHistory)
                .forEach(a -> System.out.printf("12월 %02d일 %s %s (%s)\n",
                        DateTimeUtil.getDateBy(a.getLocalDate()),
                        DateTimeUtil.getDayOfWeekBy(a.getLocalDate()),
                        formatTime(a.getLocalTime()),
                        a.getState().getState()
                ));
    }

    private static List<Attendance> sortByDate(List<Attendance> attendances) {
        return attendances.stream()
                .sorted(Comparator.comparing(Attendance::getLocalDate))
                .toList();
    }


}
