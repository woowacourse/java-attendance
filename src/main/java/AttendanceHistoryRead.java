import domain.AttendanceBook;
import domain.Attendances;

public class AttendanceHistoryRead {
    public Integer getLateCountOf(AttendanceBook attendanceBook, String nickname) {
        Attendances attendances = attendanceBook.getAttendances(nickname);
        return attendances.getLateCount();
    }

    public Integer getAbsentCountOf(AttendanceBook attendanceBook, String nickname) {
        Attendances attendances = attendanceBook.getAttendances(nickname);
        return attendances.getAbsentCount();
    }

    public Integer getAttendanceCountOf(AttendanceBook attendanceBook, String nickname) {
        Attendances attendances = attendanceBook.getAttendances(nickname);
        return attendances.getTotalCount() - attendances.getLateCount() - attendances.getAbsentCount();
    }

    public void recordAllAbsence(AttendanceBook attendanceBook) {
        attendanceBook.recordAllAbsences();
    }


}
