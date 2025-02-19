package view;

import domain.AttendanceState;
import domain.Calender;
import java.time.LocalDateTime;

public class OutputView {

    public static void printTodayAttendance(final int todayDay, final String todayDayOfWeek,
                                            final String schoolStartTime, final String attendanceResult) {
        System.out.printf("12월 %02d일 %s %s (%s)",
                todayDay, todayDayOfWeek, schoolStartTime, attendanceResult);
    }

    public static void printUpdateAttendance(final LocalDateTime beforeDateTime, final LocalDateTime afterDateTime) {

        String beforeAttendanceState = AttendanceState.findStateBy(
                beforeDateTime.toLocalTime(),
                beforeDateTime.getDayOfWeek().getValue());

        String afterAttendanceState = AttendanceState.findStateBy(
                afterDateTime.toLocalTime(),
                afterDateTime.getDayOfWeek().getValue());

        System.out.printf("12월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                beforeDateTime.getDayOfMonth(),
                Calender.findBy(beforeDateTime.getDayOfMonth()),
                beforeDateTime.getHour(),
                beforeDateTime.getMinute(),
                beforeAttendanceState,
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                afterAttendanceState
        );
    }
}
