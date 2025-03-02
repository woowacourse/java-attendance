package fixture;

import domain.AttendanceDateTime;
import domain.AttendanceDateTimes;
import domain.AttendanceHistories;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceHistoriesFixture {
    public static AttendanceHistories createWithSingleAttendance(Crew crew, LocalDateTime dateTime) {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(dateTime);
        AttendanceDateTimes attendanceDateTimes = new AttendanceDateTimes(new ArrayList<>(List.of(attendanceDateTime)));
        return new AttendanceHistories(new HashMap<>(Map.of(crew, attendanceDateTimes)));
    }

    public static AttendanceHistories createWithMultipleAttendance(Crew crew, LocalDate startDate,
                                                                   int presentCount,
                                                                   int tardyCount,
                                                                   int absentCount) {
        AttendanceDateTimes attendanceDateTimes = AttendanceDateTimesFixture.of(startDate, presentCount, tardyCount,
                absentCount);
        return new AttendanceHistories(new HashMap<>(Map.of(crew, attendanceDateTimes)));
    }
}
