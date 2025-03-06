package attendance.domain.dto;

import attendance.domain.Attendance;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public record ModifyAttendanceResult(
        int modifyMonth,
        int modifyDay,
        String modifyDayOfWeek,
        int modifyOldHour,
        int modifyOldMinute,
        String modifyOldStatus,
        int modifyNewHour,
        int modifyNewMinute,
        String modifyNewStatus

) {
    public static ModifyAttendanceResult of(List<Attendance> attendances) {
        Attendance oldAttendance = attendances.getFirst();
        Attendance newAttendance = attendances.getLast();
        return new ModifyAttendanceResult(
                oldAttendance.getAttendanceDate().getMonthValue(),
                oldAttendance.getAttendanceDate().getDayOfMonth(),
                oldAttendance.getAttendanceDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                oldAttendance.getAttendanceTime().getHour(),
                oldAttendance.getAttendanceTime().getMinute(),
                oldAttendance.getAttendanceStatus(),
                newAttendance.getAttendanceTime().getHour(),
                newAttendance.getAttendanceTime().getMinute(),
                newAttendance.getAttendanceStatus()
        );
    }
}
