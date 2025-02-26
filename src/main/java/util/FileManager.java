package util;

import domain.Attendance;
import domain.AttendanceState;
import domain.Crew;
import dto.AttendanceRecord;
import dto.Time;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileManager {

    private static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendances.csv";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Attendance readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE_PATH));
            br.readLine();

            String line;
            Map<Crew, List<AttendanceRecord>> attendances = new LinkedHashMap<>();

            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(",");

                String name = lineSplit[0];
                Crew crew = Crew.from(name);
                String dateTime = lineSplit[1];

                LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
                LocalDate localDate = localDateTime.toLocalDate();
                LocalTime localTime = localDateTime.toLocalTime();
                AttendanceState state = AttendanceState.findStateBy(localTime, localDate);

                Time time = new Time(localTime, state);
                AttendanceRecord record = new AttendanceRecord(localDate, time);

                List<AttendanceRecord> records = attendances.getOrDefault(crew, new ArrayList<>());
                records.add(record);

                attendances.put(crew, records);
            }

            return new Attendance(attendances);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
