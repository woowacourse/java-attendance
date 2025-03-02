package view;

import model.Attendance;
import model.AttendanceDateTime;
import model.AttendanceStatus;

public class TypeInfoDto {

    private static final String FORMAT = "%s (%s)";

    private final DateInfoDto infoDto;
    private final String statusDisplayName;

    private TypeInfoDto(final AttendanceDateTime attendanceDateTime, final AttendanceStatus status) {
        this.infoDto = new DateInfoDto(attendanceDateTime);
        this.statusDisplayName = status.getDisplayName();
    }

    public static TypeInfoDto of(final Attendance attendance) {
        return new TypeInfoDto(attendance.getAttendanceDateTime(), attendance.getAttendanceStatus());
    }

    public String getFormattedDateTime() {
        return String.format(FORMAT, infoDto.getFormattedDateTime(), statusDisplayName);
    }

    public String getFormattedDate() {
        return String.format(FORMAT, infoDto.getFormattedDate(), statusDisplayName);
    }

    public String getFormattedTime() {
        return String.format(FORMAT, infoDto.getFormattedTime(), statusDisplayName);
    }
}
