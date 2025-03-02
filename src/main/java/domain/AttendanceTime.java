package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import util.DateTimeConvertor;

public class AttendanceTime {

    private final LocalTime localTime;

    public AttendanceTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public static AttendanceTime of(final DayOfWeek dayOfWeek, final LocalTime localtime) {
        validateTime(dayOfWeek, localtime);
        return new AttendanceTime(localtime);
    }


    private static void validateTime(final DayOfWeek dayOfWeek, final LocalTime localtime) {
        if (CampusTime.isNotOpenTime(dayOfWeek, localtime)) {
            throw new IllegalArgumentException(String.format("[ERROR] %s %s은 캠퍼스 운영시간이 아닙니다.",
                    DateTimeConvertor.convertToDayOfWeekKoreanFormat(dayOfWeek),
                    DateTimeConvertor.convertToLocalTimeKoreanFormat(localtime)));
        }
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
