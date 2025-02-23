package attendance.domain;

import java.util.List;

public class CrewAttendanceRepositoryTestFixture {
    public static CrewAttendanceRepository createCrewAttendanceRepository() {
        List<CrewAttendance> crewAttendances = CrewAttendanceTestFixture.createCrewAttendances();
        return new CrewAttendanceRepository(crewAttendances);
    }
}
