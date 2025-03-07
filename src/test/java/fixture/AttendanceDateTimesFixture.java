package fixture;

import domain.AttendanceDateTime;
import domain.AttendanceDateTimes;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDateTimesFixture {
    public static AttendanceDateTimes of(int presentCount, int tardyCount, int absentCount) {
        List<AttendanceDateTime> attendanceDateTimes = new ArrayList<>();
        List<AttendanceDateTime> presentDates = AttendanceDateTimeFixture.createPresentDates();
        List<AttendanceDateTime> tardyDates = AttendanceDateTimeFixture.createTardyDates();
        List<AttendanceDateTime> absentDates = AttendanceDateTimeFixture.createAbsentDates();
        int globalIndex = 0;
        for (int i = 0; i < presentCount; i++, globalIndex += 1) {
            attendanceDateTimes.add(presentDates.get(globalIndex));
        }
        for (int i = 0; i < tardyCount; i++, globalIndex += 1) {
            attendanceDateTimes.add(tardyDates.get(globalIndex));
        }
        for (int i = 0; i < absentCount; i++, globalIndex += 1) {
            attendanceDateTimes.add(absentDates.get(globalIndex));
        }
        return new AttendanceDateTimes(attendanceDateTimes);
    }
}
