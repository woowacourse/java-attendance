package view;

import model.AttendanceStatus;

public class OutputView {

    public static void printAttendance(final DateInfoDto dto, final AttendanceStatus status) {
        System.out.println(String.format("%s %s (%s)", dto.getFormattedDate(), dto.getFormattedTime(), status.getDisplayName()));
    }
}
