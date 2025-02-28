package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Constants {
    public static final int ATTENDANCE_HOUR_OF_MONDAY = 13;
    public static final int ATTENDANCE_HOUR_OF_TUESDAY_TO_FRIDAY = 10;
    public static final int END_MINUTE_OF_ATTENDANCE = 5;
    public static final int END_MINUTE_OF_LATE = 30;

    public static final int START_YEAR = 2024;
    public static final int START_MONTH = 12;
    public static final List<Integer> HOLIDAY = List.of(25);

    public static final LocalTime ABSENT_CONSIDERING_TIME = LocalTime.of(15, 0);

    public static final int EXPULSION_CONDITION = 6;
    public static final int COUNSELING_CONDITION = 3;
    public static final int WARNING_CONDITION = 2;

    public static final int ABSENT_CONSIDERING_UNIT = 3;

    public static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    public static final String ERROR_HEADER = "[ERROR] ";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";

    public static final LocalDate TODAY = LocalDate.of(2024, 12, 13);
    public static final LocalDate YESTERDAY = TODAY.minusDays(1);
}
