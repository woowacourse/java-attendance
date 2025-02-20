package view;

import domain.Attendance;
import domain.CheckInTime;
import domain.PenaltyStatus;

import java.time.LocalDateTime;

public class OutputView {

    public void printTodayCheckInTime(CheckInTime time) {
        LocalDateTime localDateTime = time.toLocalDateTime();

        System.out.println(localDateTime);
    }

    public void printModifyCheckInTime(CheckInTime before, CheckInTime after) {
        System.out.println(before.toLocalDateTime() + " -> " + after.toLocalDateTime());
    }

    public void printAttendanceLog(Attendance attendance) {
        for (LocalDateTime time : attendance.getAttendanceLog()) {
            System.out.println(time);
        }
        int presenceCount = attendance.countPresence();
        int lateCount = attendance.countLate();
        int absenceCount = attendance.countAbsence();
        PenaltyStatus penaltyStatus = PenaltyStatus.getPenaltyStatus(absenceCount, lateCount);

        System.out.println("출석: " + presenceCount);
        System.out.println("지각: " + lateCount);
        System.out.println("결석: " + absenceCount);
        System.out.println(penaltyStatus.name());
    }
}
