package domain;

import static constants.AttendanceCriteria.EXCEPT_MONDAY_ATTEND;
import static constants.AttendanceCriteria.EXCEPT_MONDAY_LATE;
import static constants.AttendanceCriteria.MONDAY_ATTEND;
import static constants.AttendanceCriteria.MONDAY_LATE;
import static constants.AttendanceCriteria.OPERATING_END;
import static constants.AttendanceCriteria.OPERATING_START;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceStatus {
    public static final String ATTEND_STATUS = "출석";
    public static final String ABSENT_STATUS = "결석";
    public static final String LATE_STATUS = "지각";

    public static String judgeAttendanceStatusByDateAndTime(LocalDate date, LocalTime time) {
        String dayOfWeek = DecemberCalendar.judgeWorkingDay(date);
        if (time == null) {
            return ABSENT_STATUS;
        }

        if (dayOfWeek.equals("월요일")) {
            return judgeAttendanceByTimeAtMonday(time);
        }

        if (dayOfWeek.equals("근무일")) { // 월요일이 아닌 근무일의 경우
            return judgeAttendanceByTimeExceptMonday(time);
        }

        return dayOfWeek; // 근무일이 아닌 경우 사유 반환
    }

    private static String judgeAttendanceByTimeAtMonday(LocalTime time) {
        return judgeAttendanceByCriteriaTime(time, MONDAY_ATTEND.getTime(), MONDAY_LATE.getTime());
    }

    private static String judgeAttendanceByTimeExceptMonday(LocalTime time) {
        return judgeAttendanceByCriteriaTime(time, EXCEPT_MONDAY_ATTEND.getTime(), EXCEPT_MONDAY_LATE.getTime());
    }

    private static String judgeAttendanceByCriteriaTime(LocalTime time, LocalTime attendCriteria,
                                                        LocalTime lateCriteria) {
        if (existedOnTime(time, OPERATING_START.getTime(), attendCriteria)) {
            return ATTEND_STATUS;
        }
        if (existedOnTime(time, attendCriteria, lateCriteria)) {
            return LATE_STATUS;
        }
        if (existedOnTime(time, lateCriteria, OPERATING_END.getTime())) {
            return ABSENT_STATUS;
        }
        return null;
    }

    private static boolean existedOnTime(LocalTime time, LocalTime start, LocalTime end) {
        return !time.isBefore(start) && !time.isAfter(end);
    }
}