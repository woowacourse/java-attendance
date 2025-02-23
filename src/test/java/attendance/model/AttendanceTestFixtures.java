package attendance.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceTestFixtures {

    public static final Crew POBI = new Crew(new Nickname("포비"));
    public static final Crew NEO = new Crew(new Nickname("네오"));
    public static final LocalDateTime WEEKEND_SUNDAY = LocalDateTime.of(2024, 12, 8, 10, 0);
    public static final LocalDateTime CHRISTMAS = LocalDateTime.of(2024, 12, 25, 10, 0);
    private static final DateTimeFormatter YEAR_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private AttendanceTestFixtures() {
    }

    public static CrewGroup createCrewGroup(Crew... crews) {
        Set<Crew> crewSet = Arrays.stream(crews)
                .collect(Collectors.toUnmodifiableSet());
        return new CrewGroup(crewSet);
    }

    public static Attendance createAttendanceInRawDateTime(Crew crew, String rawDateTime) {
        return new Attendance(crew, parseDateTime(rawDateTime));
    }

    private static LocalDateTime parseDateTime(String rawDateTime) {
        return LocalDateTime.parse(rawDateTime, YEAR_DATE_TIME_FORMATTER);
    }
}
