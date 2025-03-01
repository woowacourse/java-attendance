package attendance.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AttendanceReader {
    private String fileName;

    public AttendanceReader(String fileName) {
        this.fileName = fileName;

    }

    public void load() {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + fileName);
        }
        try (
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            var lines = bufferedReader.lines()
                .skip(1)
                .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
