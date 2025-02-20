package domain;

import constants.DateConstants;

import java.time.LocalDateTime;

public class AttendanceCustomDate {
    public static LocalDateTime now() {
        LocalDateTime now = LocalDateTime.now();
        // TODO: DayOfMonth를 동적으로 설정할 수 있도록 개선하기
        return now.withYear(DateConstants.YEAR).withMonth(DateConstants.MONTH.getValue()).withDayOfMonth(26);
    }
}
