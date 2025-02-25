package util;

import domain.Attendance;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileManager {


    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SPLIT_DELIMITER = ",";

    public static Attendance readFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            br.readLine();

            return createAttendances(br);
        } catch (IOException e) {
            throw new IllegalArgumentException("잘못된 파일 입니다.");
        }
    }

    private static Attendance createAttendances(final BufferedReader br) throws IOException {
        String line;
        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();

        while ((line = br.readLine()) != null) {
            String[] lineSplit = split(line);

            Crew crew = createCrew(lineSplit);
            LocalDateTime localDateTime = createLocalDateTime(lineSplit);

            List<LocalDateTime> localDateTimes = insertLocalDateTime(attendances, crew, localDateTime);
            attendances.put(crew, localDateTimes);
        }

        return new Attendance(attendances);
    }

    private static String[] split(final String line) {
        validateSplit(line);
        return line.split(SPLIT_DELIMITER);
    }

    private static void validateSplit(final String line) {
        if (!line.contains(SPLIT_DELIMITER)) {
            throw new IllegalArgumentException("잘못된 구분자 입니다.");
        }
    }

    private static Crew createCrew(final String[] lineSplit) {
        String name = lineSplit[0];
        return Crew.from(name);
    }

    private static LocalDateTime createLocalDateTime(final String[] lineSplit) {
        String dateTime = lineSplit[1];
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
    }

    private static List<LocalDateTime> insertLocalDateTime(final Map<Crew, List<LocalDateTime>> attendances,
                                                           final Crew crew, final LocalDateTime localDateTime) {
        List<LocalDateTime> localDateTimes = attendances.getOrDefault(crew, new ArrayList<>());
        localDateTimes.add(localDateTime);
        return localDateTimes;
    }
}
