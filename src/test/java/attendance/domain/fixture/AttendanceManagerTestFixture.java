package attendance.domain.fixture;

import static attendance.domain.fixture.LocalDateTestFixture.DATE_PROVIDER;

import attendance.config.FixedLocalDateProvider;
import attendance.domain.AttendanceChecker;
import attendance.domain.AttendanceManager;
import attendance.domain.Attendances;
import attendance.domain.DefaultAttendanceChecker;
import attendance.domain.DefaultAttendanceStatistics;
import attendance.domain.LocalDateProvider;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AttendanceManagerTestFixture {

    public static AttendanceManager createEmptyManagerByName(String... names) {
        return createEmptyManager(DATE_PROVIDER, names);
    }

    public static AttendanceManager createEmptyManagerByName(LocalDate date, String... names) {
        LocalDateProvider dateProvider = new FixedLocalDateProvider(date);
        return createEmptyManager(dateProvider, names);
    }

    private static AttendanceManager createEmptyManager(LocalDateProvider dateProvider, String... names) {
        Map<String, Attendances> crewAttendances = new HashMap<>();

        Arrays.stream(names)
                .forEach(name -> {
                    crewAttendances.put(name, createEmptyAttendances());
                });
        AttendanceChecker checker = new DefaultAttendanceChecker();
        return new AttendanceManager(crewAttendances, dateProvider, new DefaultAttendanceStatistics(dateProvider, checker), checker);
    }

    public static AttendanceManager createByCrewAttendances(Map<String, Attendances> crewAttendances) {
        LocalDateProvider dateProvider = DATE_PROVIDER;
        AttendanceChecker checker = new DefaultAttendanceChecker();
        return new AttendanceManager(crewAttendances, dateProvider, new DefaultAttendanceStatistics(dateProvider, checker), checker);
    }

    private static Attendances createEmptyAttendances() {
        return new Attendances();
    }
}
