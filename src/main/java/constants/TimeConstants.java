package constants;

import java.time.LocalTime;

public class TimeConstants {
    public static final int OPERATION_START_HOUR = 8;
    public static final int OPERATION_START_MINUTE = 0;
    public static final int OPERATION_END_HOUR = 23;
    public static final int OPERATION_END_MINUTE = 0;

    public static final int MONDAY_ATTEND_END_HOUR = 13;
    public static final int MONDAY_ATTEND_END_MINUTE = 5;
    public static final int MONDAY_LATE_END_HOUR = 13;
    public static final int MONDAY_LATE_END_MINUTE = 30;

    public static final int EXCEPT_MONDAY_ATTEND_END_HOUR = 10;
    public static final int EXCEPT_MONDAY_ATTEND_END_MINUTE = 5;
    public static final int EXCEPT_MONDAY_LATE_END_HOUR = 10;
    public static final int EXCEPT_MONDAY_LATE_END_MINUTE = 30;

    public static final LocalTime OPERATION_TIME_START = LocalTime.of(OPERATION_START_HOUR, OPERATION_START_MINUTE);
    public static final LocalTime OPERATION_TIME_END = LocalTime.of(OPERATION_END_HOUR, OPERATION_END_MINUTE);

    public static final LocalTime MONDAY_ATTEND_TIME_END = LocalTime.of(MONDAY_ATTEND_END_HOUR,
            MONDAY_ATTEND_END_MINUTE);
    public static final LocalTime MONDAY_LATE_TIME_END = LocalTime.of(MONDAY_LATE_END_HOUR, MONDAY_LATE_END_MINUTE);

    public static final LocalTime EXCEPT_MONDAY_ATTEND_TIME_END = LocalTime.of(EXCEPT_MONDAY_ATTEND_END_HOUR,
            EXCEPT_MONDAY_ATTEND_END_MINUTE);
    public static final LocalTime EXCEPT_MONDAY_LATE_TIME_END = LocalTime.of(EXCEPT_MONDAY_LATE_END_HOUR,
            EXCEPT_MONDAY_LATE_END_MINUTE);
}