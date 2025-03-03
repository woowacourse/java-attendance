package fixture;

import domain.AttendanceStatus;
import domain.CampusTimePolicy;
import domain.LectureTime;
import java.time.LocalTime;

public class LocalTimeFixture {
    public static LocalTime MONDAY_ATTENDANCE = LectureTime.MONDAY.getStartTime();
    public static LocalTime MONDAY_LATE = LectureTime.MONDAY.getStartTime()
            .plusMinutes(AttendanceStatus.LATE_LIMIT_IN_MINUTES + 1);
    public static LocalTime MONDAY_ABSENT = LectureTime.MONDAY.getStartTime()
            .plusMinutes(AttendanceStatus.ABSENT_LIMIT_IN_MINUTES + 1);

    public static LocalTime TUESDAY_ATTENDANCE = LectureTime.TUESDAY.getStartTime();
    public static LocalTime TUESDAY_LATE = LectureTime.TUESDAY.getStartTime()
            .plusMinutes(AttendanceStatus.LATE_LIMIT_IN_MINUTES + 1);
    public static LocalTime TUESDAY_ABSENT = LectureTime.TUESDAY.getStartTime()
            .plusMinutes(AttendanceStatus.ABSENT_LIMIT_IN_MINUTES + 1);

    public static LocalTime LECTURE_TIME1 = LectureTime.TUESDAY.getStartTime();
    public static LocalTime LECTURE_TIME2 = LectureTime.TUESDAY.getEndTime();
    public static LocalTime CAMPUS_TIME = CampusTimePolicy.CAMPUS_OPEN_TIME;
    public static LocalTime NOT_CAMPUS_TIME = CampusTimePolicy.CAMPUS_OPEN_TIME.minusMinutes(1);
}
