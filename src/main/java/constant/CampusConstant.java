package constant;

import java.time.LocalDate;
import java.time.LocalTime;

public class CampusConstant {

    public static final int YEAR = 2024;
    public static final int DECEMBER_MONTH = 12;
    public static final int DECEMBER_START_DAY = 1;
    public static final int DECEMBER_END_DAY = 31;

    public static final int LATE_TO_ABSENT_UNIT = 3;
    public static final int LATE_TIME = 5;
    public static final int ABSENT_TIME = 30;

    public static final LocalDate DECEMBER_START_DATE = LocalDate.of(2024, 12, 1);
    public static final LocalDate DECEMBER_END_DATE = LocalDate.of(2024, 12, 31);
    public static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    public static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    public static final LocalTime STUDY_START_TIME = LocalTime.of(10, 0);
    public static final LocalTime STUDY_START_TIME_MONDAY = LocalTime.of(13, 0);

    public static final LocalDate JANUARY_START_DATE = LocalDate.of(2025, 1, 1);
}
