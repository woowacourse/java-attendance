package attendance.domain.attendanceManager;

import java.time.LocalDateTime;

import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendance.Attendances;
import attendance.utility.DateTimeFormatterWrapper;

public class ModifyManager {
    public static final String FORMAT_ATTENDANCE = "MM월 d일 E요일 HH:mm (%s) ";
    public static final String FORMAT_ATTENDANCE_NOT_EXISTING = "MM월 d일 E요일 --:-- (결석) ";
    public static final String MODIFY_TIME_FORM = "-> HH:mm (%s)";

    private final AttendanceBook attendanceBook;
    private final StringBuilder stringBuilder;

    public ModifyManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
        this.stringBuilder = new StringBuilder();
    }

    public void manage(String nickname, LocalDateTime dateTime) {
        // Attendance newAttendance = Attendance.from(dateTime);
        // Attendances attendances = attendanceBook.getAttendances(nickname);
        // Optional<Attendance> existingAttendanceOptional = attendanceBook.findAttendance(nickname,
        //     dateTime.toLocalDate());
        //
        // if (existingAttendanceOptional.isPresent()) {
        //     appendReportForExisting(existingAttendanceOptional.get(), attendances, newAttendance);
        //     return;
        // }
        // appendReportForNonExisting(attendances, newAttendance);
    }

    private void appendReportForNonExisting(Attendances attendances, Attendance newAttendance) {
        String formattedDateTime = DateTimeFormatterWrapper.getFormatter(FORMAT_ATTENDANCE_NOT_EXISTING)
            .format(newAttendance.dateTime());
        stringBuilder.append(formattedDateTime);

        attendances.add(newAttendance);
        appendNewAttendance(newAttendance);
    }

    private void appendReportForExisting(Attendance existingAttendanceOpt, Attendances attendances,
        Attendance newAttendance) {
        appendExistingAttendance(existingAttendanceOpt);
        attendances.remove(existingAttendanceOpt);
        attendances.add(newAttendance);
        appendNewAttendance(newAttendance);
    }

    private void appendExistingAttendance(Attendance attendance) {
        String status = attendance.attendanceStatus().getValue();
        String statusFormat = String.format(FORMAT_ATTENDANCE, status);
        String formattedDateTime = DateTimeFormatterWrapper.getFormatter(statusFormat)
            .format(attendance.dateTime());
        stringBuilder.append(formattedDateTime);
    }

    private void appendNewAttendance(Attendance attendance) {
        String status = attendance.attendanceStatus().getValue();
        String statusFormat = String.format(MODIFY_TIME_FORM, status);
        String formattedDateTime = DateTimeFormatterWrapper.getFormatter(statusFormat)
            .format(attendance.dateTime());
        stringBuilder.append(formattedDateTime);
    }

    public String getResult() {
        return stringBuilder.toString();
    }
}
