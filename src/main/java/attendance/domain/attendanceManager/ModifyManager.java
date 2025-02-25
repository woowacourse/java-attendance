package attendance.domain.attendanceManager;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.time.LocalDateTime;
import java.util.Optional;

import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendance.AttendanceList;

public class ModifyManager {
    public static final String ATTENDANCE_INFO = "MM월 d일 E요일 HH:mm (%s) ";
    public static final String ATTENDANCE_INFO_NOT_EXISTING = "MM월 d일 E요일 --:-- (결석) ";
    public static final String MODIFY_TIME_FORM = "-> HH:mm (%s)";

    private final AttendanceBook attendanceBook;
    private final StringBuilder report;

    public ModifyManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
        this.report = new StringBuilder();
    }

    public void manage(String nickname, LocalDateTime dateTime) {
        Attendance newAttendance = new Attendance(dateTime);
        AttendanceList attendanceList = attendanceBook.getAttendanceList(nickname);
        Optional<Attendance> existingAttendanceOpt = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());

        if (existingAttendanceOpt.isPresent()) {
            appendReportForExisting(existingAttendanceOpt.get(), attendanceList, newAttendance);
            return;
        }
        appendReportForNonExisting(attendanceList, newAttendance);
    }

    private void appendReportForNonExisting(AttendanceList attendanceList, Attendance newAttendance) {
        String formattedDateTime = getFormatter(ATTENDANCE_INFO_NOT_EXISTING).format(newAttendance.dateTime());
        report.append(formattedDateTime);

        attendanceList.add(newAttendance);
        appendNewAttendanceInfo(newAttendance);
    }

    private void appendReportForExisting(Attendance existingAttendanceOpt, AttendanceList attendanceList,
        Attendance newAttendance) {
        appendExistingAttendanceInfo(existingAttendanceOpt);
        attendanceList.remove(existingAttendanceOpt);
        attendanceList.add(newAttendance);
        appendNewAttendanceInfo(newAttendance);
    }

    private void appendExistingAttendanceInfo(Attendance attendance) {
        String status = attendance.attendanceStatus().getValue();
        String statusFormat = String.format(ATTENDANCE_INFO, status);
        String formattedDateTime = getFormatter(statusFormat).format(attendance.dateTime());
        report.append(formattedDateTime);
    }

    private void appendNewAttendanceInfo(Attendance attendance) {
        String status = attendance.attendanceStatus().getValue();
        String statusFormat = String.format(MODIFY_TIME_FORM, status);
        String formattedDateTime = getFormatter(statusFormat).format(attendance.dateTime());
        report.append(formattedDateTime);
    }

    public String getResult() {
        return report.toString();
    }
}
