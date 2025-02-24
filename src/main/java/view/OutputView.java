package view;

import common.Common;
import model.Attendance;

public class OutputView {
    public void printAttendanceRegisterResult(Attendance newAttendance) {
        System.out.printf("%s %s (%s)\n",
                newAttendance.getDate().format(Common.monthDateDayFormatter),
                newAttendance.getTime().format(Common.hourMinuteFormatter),
                newAttendance.findStatus().getMeaning()
        );
    }
}
