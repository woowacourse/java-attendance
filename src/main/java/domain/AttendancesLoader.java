package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class AttendancesLoader {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Attendances load(FileReader fileReader) throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);
        skipTitleLine(reader);

        Attendances attendances = new Attendances(new HashMap<>());
        addAttendanceLog(reader, attendances);

        return attendances;
    }

    private void addAttendanceLog(BufferedReader reader, Attendances attendances) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] nameAndDatetime = line.split(",");
            String nickname = nameAndDatetime[0];
            String datetime = nameAndDatetime[1];
            LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter);

            attendances.addAttendanceLog(nickname, localDateTime);
        }
    }

    private void skipTitleLine(BufferedReader reader) throws IOException {
        reader.readLine();
    }
}
