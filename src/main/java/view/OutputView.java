package view;

import model.AttendanceStatus;

public class OutputView {

    public static void printAttendance(final DateInfoDto dto, final AttendanceStatus status) {
        System.out.println(String.format("%s (%s)", dto.getFormattedDateTime(), status.getDisplayName()));
    }

    public static void printAttendanceCorrection(final DateInfoDto oldDto, final AttendanceStatus oldStatus, final DateInfoDto newDto, final AttendanceStatus newStatus) {
        System.out.println(String.format("%s (%s) -> %s (%s) 수정 완료!", oldDto.getFormattedDateTime(), oldStatus.getDisplayName(), newDto.getFormattedTime(), newStatus.getDisplayName()));
    }
}
