package attendance.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {
    public List<List<String>> readResource(String fileName) {
        try (InputStream resource = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource));

            return convertResource(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file"); //TODO constant: no file exists
        }
    }

    private List<List<String>> convertResource(BufferedReader reader) throws IOException {
        List<List<String>> attendanceRecords = new ArrayList<>();
        validateFileContent(reader.readLine());

        String line;
        while ((line = reader.readLine()) != null) {
            List<String> attendanceRecord = Arrays.asList(line.split(","));   //TODO constant: delimiter
            attendanceRecords.add(attendanceRecord);
        }
        return attendanceRecords;
    }

    private void validateFileContent(String firstLine) {
        if (firstLine == null) {
            throw new IllegalStateException("File is empty"); //TODO constant: empty file content
        }
    }
}
