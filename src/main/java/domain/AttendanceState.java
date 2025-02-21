package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import util.DateTimeUtil;

public enum AttendanceState {

    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private final String description;

    AttendanceState(String description) {
        this.description = description;
    }

    public static String findStateBy(final LocalTime localTime, final LocalDate localDate) {
        Calender.validateHolyDay(DateTimeUtil.getDateBy(localDate)); // 크리스마스 고려 안 됨...
        AttendanceTime.validateCampusTime(localTime);

//        String dayOfWeek = Calender.findBy(DateTimeUtil.nowDate());

        String dayOfWeek = DateTimeUtil.getDayOfWeekBy(
                LocalDate.of(DateTimeUtil.getYearBy(localDate), DateTimeUtil.getMonthBy(localDate),
                        DateTimeUtil.getDateBy(localDate)));
//        String dayOfWeek = LocalDate.of(2024, 12, date).getDayOfWeek().toString();

        if (dayOfWeek.equals("월요일")) {
            if (localTime.isAfter(AttendanceTime.MON_TIME.getLocalTimes().get(1))) {
                return ABSENCE.description;
            } else if (localTime.isAfter(AttendanceTime.MON_TIME.getLocalTimes().get(0))) {
                return LATENESS.description;
            }
            return ATTENDANCE.description;
        }

        if (!dayOfWeek.equals("월요일") && !dayOfWeek.equals("공휴일")) {
            if (localTime.isAfter(AttendanceTime.ELSE_TIME.getLocalTimes().get(1))) {
                return ABSENCE.description;
            } else if (localTime.isAfter(AttendanceTime.ELSE_TIME.getLocalTimes().get(0))) {
                return LATENESS.description;
            }
            return ATTENDANCE.description;
        }

        return null;
    }

    public String getDescription() {
        return description;
    }
}
