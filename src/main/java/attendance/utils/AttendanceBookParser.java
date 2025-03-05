package attendance.utils;

import attendance.domain.Crew;
import attendance.domain.Crews;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendanceBookParser {
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String HYPHEN = "-";
    private static final String COLON = ":";
    private static final int POSITION_ZERO = 0;
    private static final int POSITION_ONE = 1;
    private static final int POSITION_TWO = 2;
    private final Crews crews;
    private final Map<Crew, List<LocalDateTime>> originalAttendanceBook = new HashMap<>();

    public AttendanceBookParser(List<String> lines) {
        Set<Crew> crewSet = new HashSet<>();
        for (String line : lines) {
            List<String> dividedLines = List.of(line.split(COMMA));
            Crew crew = new Crew(dividedLines.getFirst());
            crewSet.add(crew);
            LocalDateTime attendanceTime = parseDateTime(dividedLines.getLast());
            originalAttendanceBook.computeIfAbsent(crew, k -> new ArrayList<>()).add(attendanceTime);
        }
        this.crews = new Crews(crewSet);
    }

    private LocalDateTime parseDateTime(String dateTime) {
        String date = make(dateTime, SPACE, POSITION_ZERO);
        String timeNumber = make(dateTime, SPACE, POSITION_ONE);
        int hour = Integer.parseInt(make(timeNumber, COLON, POSITION_ZERO));
        int minute = Integer.parseInt(make(timeNumber, COLON, POSITION_ONE));
        int year = Integer.parseInt(make(date, HYPHEN, POSITION_ZERO));
        int month = Integer.parseInt(make(date, HYPHEN, POSITION_ONE));
        int day = Integer.parseInt(make(date, HYPHEN, POSITION_TWO));
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private String make(String standard, String delimiter, int findIndex) {
        return List.of(standard.split(delimiter)).get(findIndex);
    }

    public Crews getCrews() {
        return crews;
    }

    public Map<Crew, List<LocalDateTime>> getOriginalAttendanceBook() {
        return originalAttendanceBook;
    }

}
