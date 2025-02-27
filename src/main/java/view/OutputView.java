package view;

import domain.Attendance;
import domain.policy.AbsentPolicy;
import domain.policy.AttendanceState;
import domain.policy.ExpellState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

import static config.AppConfig.TODAY;
import static domain.policy.AttendanceState.*;
import static view.ViewMessage.*;

public class OutputView {

    public void printAttendanceSheetIntro(String nickname) {
        System.out.printf(ViewMessage.CURRENT_MONTH_ATTENDANCE_SHEET, nickname);
    }

    public void printAttendancesSheet(String nickname, List<Attendance> attendanceByNickname) {
        List<Attendance> attendancesForPrint = new ArrayList<>(attendanceByNickname);

        for(int day = 1; day < TODAY.getDayOfMonth(); day++) {
            LocalDate date = LocalDate.of(TODAY.getYear(), TODAY.getMonth(), day);

            int finalDay = day;
            if(attendancesForPrint.stream()
                    .noneMatch(attendance -> attendance.getDate().getDayOfMonth() == finalDay)){
                attendancesForPrint.add(new Attendance(nickname, date, null, AttendanceState.ABSENT));
            }
        }

        printAttendanceSheet(attendancesForPrint);
        System.out.print(System.lineSeparator());
    }

    private void printAttendanceSheet(List<Attendance> attendancesForPrint) {
        AbsentPolicy absentPolicy = new AbsentPolicy();

        attendancesForPrint.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .filter(attendance -> absentPolicy.isNotHoliday(attendance.getDate()))
                .filter(attendance -> absentPolicy.isWeekday(attendance.getDate().getDayOfWeek()))
                .map(this::printAttendance)
                .forEach(System.out::print);
    }

    private String printAttendance(Attendance attendance) {
        LocalDate date = attendance.getDate();
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

            return String.format(ATTENDANCE_FORMAT,
                    date.getDayOfMonth(), dayOfWeek, getTime(attendance.getTime()), attendance.getState().description);
    }

    private String getTime(LocalTime time) {
        if(time == null) return "--:--";
        return String.format(TIME_FORMAT, time.getHour(), time.getMinute());
    }

    public void printAttendanceStatistics(Map<AttendanceState, Long> attendanceState) {
        System.out.printf(STATISTICS_FORMAT, attendanceState.get(ATTENDANCE), attendanceState.get(LATE), attendanceState.get(ABSENT));
        System.out.print(System.lineSeparator().repeat(2));
    }

    public void printAbsentPolicy(ExpellState expellState) {
        System.out.printf(ABSENT_POLICY_FORMAT, expellState.state);
    }
}
