package domain;

import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import strategy.NowDateStrategy;

public class DateCrewAttendanceManager {
    
    private final Map<LocalDate, CrewAttendance> dateCrewAttendances;
    private final NowDateStrategy nowDateStrategy;
    private final AttendanceDateHelper attendanceDateHelper;

    public DateCrewAttendanceManager(NowDateStrategy nowDateStrategy) {
        this.nowDateStrategy = nowDateStrategy;
        this.dateCrewAttendances = new HashMap<>();
        this.attendanceDateHelper = new AttendanceDateHelper();
    }

    public void addAttendance(LocalTime attendanceTime) {
        LocalDate attendanceDate = nowDateStrategy.now();
        validateAttendanceDate(attendanceDate);
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime, AttendanceStatus.ATTENDANCE));
    }

    private void validateAttendanceDate(LocalDate attendanceDate) {
        if (AttendanceDateHelper.isWeekend(attendanceDate)) {
            throw new AttendanceException(AttendanceDateHelper.NOT_SCHOOL_RUNNING_DAY);
        }
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        return dateCrewAttendances.get(date);
    }
}
