package attendance.domain.attendanceManager;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.time.LocalDateTime;

import attendance.common.exception.AttendanceArgumentException;
import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendance.AttendanceList;

public class RegisterManager {
    public static final String DUPLICATE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";
    public static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";
    public static final String ATTENDANCE_INFO = "MM월 d일 E요일 HH:mm ";
    public static final String dd = "(%s)";

    private final AttendanceBook attendanceBook;
    private final StringBuilder builder = new StringBuilder();

    public RegisterManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void manage(String nickname, LocalDateTime dateTime) {
        try {
            var attendance = new Attendance(dateTime);
            var attendanceList = attendanceBook.attendances().get(nickname);
            isDuplicateAttendance(attendance, attendanceList);
            attendanceList.add(attendance);

            String formattedDateTime = getFormatter(ATTENDANCE_INFO).format(dateTime);
            String status = attendance.attendanceStatus().getValue();
            String formattedStatus = String.format(dd, status);
            builder.append(formattedDateTime)
                .append(formattedStatus);
        } catch (NullPointerException e) {
            throw new AttendanceArgumentException(NOT_REGISTERED_NICKNAME);
        }
    }

    public String getResult() {
        return builder.toString();
    }

    private void isDuplicateAttendance(Attendance attendance, AttendanceList attendanceList) {
        if (attendanceList.contains(attendance)) {
            throw new AttendanceArgumentException(DUPLICATE_DATE);
        }
    }

}
