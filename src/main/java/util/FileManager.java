package util;

import domain.Attendance;
import domain.Attendances;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SPLIT_DELIMITER = ",";

    public static Attendances readFile(final String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            oneLineSkip(br);

            List<Attendance> crewAttendances = new ArrayList<>();
            createCrewAttendances(br, crewAttendances);

            return insertAttendances(crewAttendances);
        } catch (IOException e) {
            throw new IllegalArgumentException("잘못된 파일 입니다.");
        }
    }

    private static void createCrewAttendances(final BufferedReader br, final List<Attendance> crewAttendances)
            throws IOException {
        String line;
        Map<Crew, Attendance> attendanceMap = new HashMap<>();
        while ((line = oneLineSkip(br)) != null) {
            String[] lineSplit = lineSplit(line);

            Crew crew = createCrew(lineSplit);
            LocalDateTime localDateTime = createLocalDateTime(lineSplit);

            if (isContainsCrew(attendanceMap, crew, localDateTime)) {
                continue;
            }

            Attendance attendance = createAttendance(crew, localDateTime);
            attendanceMap.put(crew, attendance);
            crewAttendances.add(attendance);
        }
    }

    private static Crew createCrew(final String[] split) {
        String splitName = split[0].trim();
        return Crew.of(splitName);
    }

    private static LocalDateTime createLocalDateTime(final String[] split) {
        String splitLocalDateTime = split[1].trim();
        return LocalDateTime.parse(splitLocalDateTime, DATE_TIME_FORMAT);
    }

    private static boolean isContainsCrew(final Map<Crew, Attendance> attendanceMap, final Crew crew,
                                          final LocalDateTime localDateTime) {
        if (attendanceMap.containsKey(crew)) {
            attendanceMap.get(crew).add(localDateTime);
            return true;
        }
        return false;
    }

    private static Attendance createAttendance(final Crew crew, final LocalDateTime localDateTime) {
        Attendance attendance = Attendance.of(crew);
        attendance.add(localDateTime);
        return attendance;
    }

    private static String[] lineSplit(final String line) {
        validateDelimiter(line);
        return line.split(SPLIT_DELIMITER);
    }

    private static void validateDelimiter(final String line) {
        if (!line.contains(SPLIT_DELIMITER)) {
            throw new IllegalArgumentException("파일의 구분자가 잘못되었습니다.");
        }
    }

    private static Attendances insertAttendances(final List<Attendance> crewAttendances) {
        Attendances attendances = new Attendances();

        for (Attendance attendance : crewAttendances) {
            attendances.add(attendance);
        }

        return attendances;
    }

    private static String oneLineSkip(final BufferedReader br) throws IOException {
        return br.readLine();
    }
}
