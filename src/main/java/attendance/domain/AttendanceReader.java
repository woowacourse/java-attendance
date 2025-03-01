package attendance.domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class AttendanceReader {
    private static final String CANT_FIND_FILE = "[ERROR] 파일을 찾을 수 없습니다: ";
    private static final String CANT_READ_FILE = "[ERROR] 파일을 읽을 수 없습니다: ";
    private final String fileName;

    public AttendanceReader(String fileName) {
        this.fileName = fileName;
    }

    public void load() throws FileNotFoundException {
        try (
            InputStream inputStream = Optional.ofNullable(getClass().getResourceAsStream(fileName))
                .orElseThrow(() -> new FileNotFoundException(CANT_FIND_FILE + fileName));
            var inputStreamReader = new InputStreamReader(inputStream);
            var bufferedReader = new BufferedReader(inputStreamReader)) {
            readFile(bufferedReader);
        } catch (IOException e) {
            throw new FileNotFoundException(CANT_READ_FILE + e);
        }
    }

    private static void readFile(BufferedReader bufferedReader) {
        var lines = bufferedReader.lines()
            .skip(1)
            .toList();
    }
}
