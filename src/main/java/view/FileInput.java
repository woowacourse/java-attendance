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
        List<AttendanceRecordDto> attendances = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(DELIMITER);
                attendances.add(new AttendanceRecordDto(split[0], Parser.stringToLocalDateTime(split[1])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return attendances;
    }
}
