package fixture;

import domain.AttendanceDateTime;
import domain.AttendanceDateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDateTimesFixture {
    public static AttendanceDateTimes of(LocalDate startDate, int presentCount, int tardyCount, int absentCount) {
        AttendanceDateTimeFixture attendanceDateTimeFixture = new AttendanceDateTimeFixture(startDate);
        List<AttendanceDateTime> attendanceDateTimes = new ArrayList<>();
        for (int i = 0; i < presentCount; i++) {
            attendanceDateTimes.add(attendanceDateTimeFixture.createPresentDateTime());
        }
        for (int i = 0; i < tardyCount; i++) {
            attendanceDateTimes.add(attendanceDateTimeFixture.createTardyDateTime());
        }
        for (int i = 0; i < absentCount; i++) {
            attendanceDateTimes.add(attendanceDateTimeFixture.createAbsentDateTime());
        }
        return new AttendanceDateTimes(attendanceDateTimes);
    }
}
