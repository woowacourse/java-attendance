package domain.policy;

import static domain.policy.AttendanceState.ABSENT;
import static domain.policy.AttendanceState.ATTENDANCE;
import static domain.policy.AttendanceState.LATE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AbsentPolicy {

    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    private static final int LATE_TIME_LIMIT = 31;
    private static final int ATTENDANCE_TIME_LIMIT = 6;

    private static final LocalTime MONDAY_EDUCATION_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime STANDARD_EDUCATION_START_TIME = LocalTime.of(10, 0);

    public boolean isWeekday(DayOfWeek attendanceDayOfWeek) {
        return !(attendanceDayOfWeek == DayOfWeek.SATURDAY || attendanceDayOfWeek == DayOfWeek.SUNDAY);
    }

    public boolean isNotHoliday(LocalDate date) {
        return !date.equals(CHRISTMAS);
    }

    private void validateIsWeekend(DayOfWeek attendanceDayOfWeek) {
        if (attendanceDayOfWeek == DayOfWeek.SATURDAY || attendanceDayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 출석할 수 없습니다");
        }
    }

    private void validateIsHoliday(LocalDate attendanceDate) {
        if (attendanceDate.equals(CHRISTMAS)) {
            throw new IllegalArgumentException("[ERROR] 공휴일에는 출석할 수 없습니다");
        }
    }

    public AttendanceState checkAttendanceStatus(LocalDateTime educationDateTime) {
        LocalDate educationDate = educationDateTime.toLocalDate();
        LocalTime educationTime = educationDateTime.toLocalTime();
        DayOfWeek attendanceDayOfWeek = educationDate.getDayOfWeek();

        validateIsWeekend(attendanceDayOfWeek);
        validateIsHoliday(educationDate);

        LocalTime attendanceTime = calculateAttendanceTime(attendanceDayOfWeek);

        return calculateAttendanceStatus(educationTime, attendanceTime);
    }

    private static AttendanceState calculateAttendanceStatus(LocalTime educationTime, LocalTime attendanceTime) {
        if (isAttendanceTime(educationTime, attendanceTime)) {
            return ATTENDANCE;
        }

        if (isLateTime(educationTime, attendanceTime)) {
            return LATE;
        }

        return ABSENT;
    }

    private static boolean isLateTime(LocalTime educationTime, LocalTime attendanceTime) {
        return educationTime.isBefore(attendanceTime.plusMinutes(LATE_TIME_LIMIT));
    }

    private static boolean isAttendanceTime(LocalTime educationTime, LocalTime attendanceTime) {
        return educationTime.isBefore(attendanceTime.plusMinutes(ATTENDANCE_TIME_LIMIT));
    }

    private static LocalTime calculateAttendanceTime(DayOfWeek attendanceDayOfWeek) {
        if (attendanceDayOfWeek == DayOfWeek.MONDAY) {
            return MONDAY_EDUCATION_START_TIME;
        }
        return STANDARD_EDUCATION_START_TIME;
    }

}
