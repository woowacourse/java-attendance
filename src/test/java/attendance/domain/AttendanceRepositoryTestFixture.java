package attendance.domain;

import java.util.List;

public class AttendanceRepositoryTestFixture {
    public static AttendanceRepository createAttendanceRepository() {
        List<Attendance> attendances = AttendanceTestFixture.createAttendances();
        return new AttendanceRepository(attendances);
    }
}
