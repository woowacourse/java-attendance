package attendance.domain.fixture;

import attendance.domain.AttendanceManager;
import attendance.domain.Attendances;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AttendanceManagerTestFixture {

    public static AttendanceManager createEmptyManagerByName(String... names) {
        Map<String, Attendances> crewAttendances = new HashMap<>();

        Arrays.stream(names)
                .forEach(name -> {
                    crewAttendances.put(name, createEmptyAttendances());
                });

        return new AttendanceManager(crewAttendances);
    }

    private static Attendances createEmptyAttendances() {
        return new Attendances();
    }
}
