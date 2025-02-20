package attendance.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {
    public List<List<String>> readResource(final String fileName) {
        try (InputStream resource = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource));

            return convertResource(reader);
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 읽는 데 실패했습니다.");
        }
    }

    private List<List<String>> convertResource(final BufferedReader reader) throws IOException {
        List<List<String>> attendanceRecords = new ArrayList<>();
        validateFileContent(reader.readLine());

        String line;
        while ((line = reader.readLine()) != null) {
            List<String> attendanceRecord = Arrays.asList(line.split(","));
            attendanceRecords.add(attendanceRecord);
        }
        return attendanceRecords;
    }

    private void validateFileContent(final String firstLine) {
        if (firstLine == null) {
            throw new IllegalStateException("[ERROR] 파일 내용이 없습니다.");
        }
    }
}
