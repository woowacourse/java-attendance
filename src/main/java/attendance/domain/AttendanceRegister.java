package attendance.domain;

import attendance.common.exception.AttendanceArgumentException;

public class AttendanceRegister extends AttendanceManager {

    public AttendanceRegister(AttendanceBook attendanceBook) {
        super(attendanceBook);
    }

    @Override
    public void manage(String nickname, Attendance attendance) {
        try {
            var attendanceList = attendanceBook.attendances().get(nickname);
            isDuplicateAttendance(attendance, attendanceList);
            attendanceList.add(attendance);
        } catch (NullPointerException e) {
            throw new AttendanceArgumentException(Error.NOT_REGISTERED_NICKNAME.getMessage());
        }
    }

    private void isDuplicateAttendance(Attendance attendance, AttendanceList attendanceList) {
        if (attendanceList.contains(attendance)) {
            throw new AttendanceArgumentException(Error.DUPLICATE_DATE.getMessage());
        }
    }
}
