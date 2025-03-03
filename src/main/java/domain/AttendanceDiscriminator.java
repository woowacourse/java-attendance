package domain;

import static domain.policy.AttendancePolicy.ABSENT_STATUS;
import static domain.policy.AttendancePolicy.ATTEND_STATUS;
import static domain.policy.AttendancePolicy.LATE_STATUS;
import static domain.policy.TimePolicy.NORMAL_ATTEND_DEAD_LINE;
import static domain.policy.TimePolicy.NORMAL_LATE_DEAD_LINE;
import static domain.policy.TimePolicy.OPERATING_END;
import static domain.policy.TimePolicy.OPERATING_START;
import static domain.policy.TimePolicy.SPECIAL_ATTEND_DEAD_LINE;
import static domain.policy.TimePolicy.SPECIAL_LATE_DEAD_LINE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceDiscriminator {
    public static String judgeTimeLogForStatus(LocalDate date, LocalTime time) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        if (time == null) { // 시간 값이 존재하지 않는 경우 결석이다.
            return ABSENT_STATUS.getStatus();
        }

        if (dayOfWeek.equals(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.KOREAN))) {
            return judgeAttendanceByTimeAtSpecialDay(time);
        }

        return judgeAttendanceByTimeAtNormalDay(time);
    }

    private static String judgeAttendanceByTimeAtSpecialDay(LocalTime time) {
        return judgeAttendanceByCriteriaTime(time, SPECIAL_ATTEND_DEAD_LINE.getTime(),
                SPECIAL_LATE_DEAD_LINE.getTime());
    }

    private static String judgeAttendanceByTimeAtNormalDay(LocalTime time) {
        return judgeAttendanceByCriteriaTime(time, NORMAL_ATTEND_DEAD_LINE.getTime(), NORMAL_LATE_DEAD_LINE.getTime());
    }

    private static String judgeAttendanceByCriteriaTime(LocalTime time, LocalTime attendCriteria,
                                                        LocalTime lateCriteria) {
        if (!time.isBefore(OPERATING_START.getTime()) && !time.isAfter(attendCriteria)) {
            return ATTEND_STATUS.getStatus();
        }
        if (!time.isBefore(attendCriteria) && !time.isAfter(lateCriteria)) {
            return LATE_STATUS.getStatus();
        }
        if (!time.isBefore(lateCriteria) && !time.isAfter(OPERATING_END.getTime())) {
            return ABSENT_STATUS.getStatus();
        }
        return null;
    }
}