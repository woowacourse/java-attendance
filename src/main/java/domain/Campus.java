package domain;

import constant.CampusConstant;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.DayOfWeekConvertor;

public class Campus {

    public static void validateCampusOpenDate(LocalDate nowDate) {
        if (isClosed(nowDate)) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s요일은 등교일이 아닙니다.", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                    DayOfWeekConvertor.convertDayOfWeekToKorean(nowDate.getDayOfWeek())));
        }
    }

    private static boolean isClosed(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(
                CampusConstant.CHRISTMAS);
    }

    public static void validateOpenHours(LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        LocalTime openHour = CampusConstant.CAMPUS_OPEN_TIME;
        LocalTime closeHour = CampusConstant.CAMPUS_CLOSE_TIME;
        if (attendanceTime.isBefore(openHour) || attendanceTime.isAfter(closeHour)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }
}
