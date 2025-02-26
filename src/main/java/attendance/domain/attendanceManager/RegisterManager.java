package attendance.domain.attendanceManager;

import java.time.LocalDateTime;

import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendance.Attendances;
import attendance.exception.AttendanceArgumentException;

public class RegisterManager {
    public static final String DUPLICATE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";
    public static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";
    public static final String ATTENDANCE_FORMAT = "MM월 d일 E요일 HH:mm ";
    public static final String FORMAT_STATUS = "(%s)";

    private final AttendanceBook attendanceBook;
    private final StringBuilder report = new StringBuilder();

    public RegisterManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void manage(String nickname, LocalDateTime dateTime) {
        try {
            var attendance = new Attendance(dateTime);
            var attendanceList = attendanceBook.attendances().get(nickname);
            validateDuplicate(attendance, attendanceList);
            attendanceList.add(attendance);
            writeDateTime(dateTime);
            writeStatus(attendance);
        } catch (NullPointerException e) {
            throw new AttendanceArgumentException(NOT_REGISTERED_NICKNAME);
        }
    }

    private void validateDuplicate(Attendance attendance, Attendances attendances) {
        if (attendances.contains(attendance)) {
            throw new AttendanceArgumentException(DUPLICATE_DATE);
        }
    }

    private void writeDateTime(LocalDateTime dateTime) {
        String formattedDateTime = getFormatter(ATTENDANCE_FORMAT).format(dateTime);
        report.append(formattedDateTime);
    }

    private void writeStatus(Attendance attendance) {
        String status = attendance.attendanceStatus().getValue();
        String formattedStatus = String.format(FORMAT_STATUS, status);
        report.append(formattedStatus);
    }

    public String getResult() {
        return report.toString();
    }

}
