package attendance.domain;

import java.util.List;

public class CrewsTestFixture {
    public static Crews createAttendanceRepository() {
        List<Crew> attendances = CrewTestFixture.createCrew();
        return new Crews(attendances);
    }
}
