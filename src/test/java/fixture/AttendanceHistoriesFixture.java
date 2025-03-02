package fixture;

import domain.AttendanceDateTimes;
import domain.AttendanceHistories;
import domain.Crew;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceHistoriesFixture {
    public static AttendanceHistories createWithSingleAttendance(Crew crew, LocalDateTime dateTime) {
        Map<Crew, AttendanceDateTimes> attendanceHistoryData = new HashMap<>();
        AttendanceDateTimes attendanceDateTimes = new AttendanceDateTimes(List.of(dateTime));
        attendanceHistoryData.put(crew, attendanceDateTimes);
        return new AttendanceHistories(attendanceHistoryData);
    }
}
