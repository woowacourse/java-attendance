package attendance.domain;

import java.time.LocalTime;

public class AttendanceManagerHelper {
    static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    static final LocalTime NORMAL_START_TIME = LocalTime.of(10, 0);
    static final LocalTime SCHOOL_OPEN_TIME = LocalTime.of(8, 0);
    static final LocalTime SCHOOL_CLOSE_TIME = LocalTime.of(23, 0);
    static final int MONDAY = 1;
    static final int LATE_MINUTE = 5;
    static final int WEEKEND_NUMBER = 6;
    static final int ABSENCE_MINUTE = 30;
    static final String NICKNAME_NOT_EXISTS = "출석 정보가 존재하지 않습니다.";
    static final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";
    static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";
    static final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";
}
