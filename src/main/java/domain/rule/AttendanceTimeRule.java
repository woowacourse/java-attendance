package domain.rule;

import util.FormatUtil;

import java.time.LocalTime;

public enum AttendanceTimeRule {

    NORMAL_ATTEND_LIMIT_TIME("일반 등교 시간", 10, 0),
    SPECIAL_ATTEND_LIMIT_TIME("특별 등교 시간", 13, 0),

    CAMPUS_OPEN_TIME("캠퍼스 출입 가능 시작 시간", 8, 0),
    CAMPUS_CLOSE_TIME("캠퍼스 출입 가능 끝 시간", 23, 0),
    ;

    public final String description;
    public final int hour;
    public final int minute;

    AttendanceTimeRule(String description, int hour, int minute) {
        this.description = description;
        this.hour = hour;
        this.minute = minute;
    }

    public static LocalTime getAttendLimitTime(boolean isSpecialDay) {
        if (isSpecialDay) {
            return SPECIAL_ATTEND_LIMIT_TIME.toLocalTime();
        }
        return NORMAL_ATTEND_LIMIT_TIME.toLocalTime();
    }

    public static void validateEnterTime(LocalTime enterTime) {
        if (enterTime.isBefore(AttendanceTimeRule.CAMPUS_OPEN_TIME.toLocalTime())) {
            throw new IllegalArgumentException(String.format("캠퍼스 출입은 %s시 이후에만 가능합니다.",
                    CAMPUS_OPEN_TIME.toLocalTime().format(FormatUtil.TIME_FORMATTER)));
        }

        if (enterTime.isAfter(AttendanceTimeRule.CAMPUS_CLOSE_TIME.toLocalTime())) {
            throw new IllegalArgumentException(String.format("캠퍼스 출입은 %s시 이전에만 가능합니다.",
                    CAMPUS_CLOSE_TIME.toLocalTime().format(FormatUtil.TIME_FORMATTER)));
        }
    }

    public LocalTime toLocalTime() {
        return LocalTime.of(this.hour, this.minute);
    }
}
