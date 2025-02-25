package view;

import common.Common;
import model.Attendance;

public class OutputView {
    public void printAttendanceRegisterResult(Attendance newAttendance) {
        System.out.println();
        System.out.printf("%s %s (%s)%n",
                newAttendance.getDate().format(Common.monthDateDayFormatter),
                newAttendance.getTime().format(Common.hourMinuteFormatter),
                newAttendance.findStatus().getMeaning()
        );
    }

    public void printExceptionMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printAttendanceModifyResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.println();
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
                oldAttendance.getDate().format(Common.monthDateDayFormatter),
                getAttendanceTimeExpression(oldAttendance),
                oldAttendance.findStatus().getMeaning(),
                getAttendanceTimeExpression(newAttendance),
                newAttendance.findStatus().getMeaning()
        );
    }

    private String getAttendanceTimeExpression(Attendance attendance) {
        if (attendance.getTime().equals(Common.noneAttendanceTime)) {
            return "--:--";
        }
        return attendance.getTime().format(Common.hourMinuteFormatter);
    }
}
