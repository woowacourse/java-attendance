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
    private static final String OUT_OF_SCHOOL_OPEN_DATE = "2024년 12월에만 출석할 수 있습니다.";
    private static final String NOT_SCHOOL_RUNNING_DAY = "휴일에는 출석할 수 없습니다.";


    public DateCrewAttendanceManager(NowDateStrategy nowDateStrategy) {
        this.nowDateStrategy = nowDateStrategy;
        this.dateCrewAttendances = new HashMap<>();
        this.attendanceDateHelper = new AttendanceDateHelper();
    }

    public void addAttendance(LocalTime attendanceTime) {
        LocalDate attendanceDate = nowDateStrategy.now();
        validateAttendanceDate(attendanceDate);
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateAttendanceStatus(attendanceTime,
                AttendanceDateHelper.schoolOpenTime(attendanceDate));
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime, attendanceStatus));
    }

    private void validateAttendanceDate(LocalDate attendanceDate) {
        if (AttendanceDateHelper.isWeekend(attendanceDate)) {
            throw new AttendanceException(NOT_SCHOOL_RUNNING_DAY);
        }
        if (AttendanceDateHelper.isOutOfSchoolOpenDate(attendanceDate)) {
            throw new AttendanceException(OUT_OF_SCHOOL_OPEN_DATE);
        }
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        return dateCrewAttendances.get(date);
    }
}
