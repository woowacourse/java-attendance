package view;

import dto.AttendanceRecordDto;
import util.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileInput {

    private static final String FILE_PATH = "attendances.csv";
    private static final String DELIMITER = ",";

    public List<AttendanceRecordDto> getFileInit() {
        try (BufferedReader reader = createBufferedReader()) {
            return getAttendanceRecords(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader createBufferedReader() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    private List<AttendanceRecordDto> getAttendanceRecords(BufferedReader reader) throws IOException {
        List<AttendanceRecordDto> attendances = new ArrayList<>();
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(DELIMITER);
            attendances.add(new AttendanceRecordDto(split[0], Parser.stringToLocalDateTime(split[1])));
        }
        return attendances;
    }
}
