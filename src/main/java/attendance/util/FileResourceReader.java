package attendance.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileResourceReader {

    private static final int HEADER = 1;

    private FileResourceReader() {}

    public static List<String> read(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.lines()
                    .skip(HEADER)
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.FILE_READ_ERROR.getMessage(), e);
        }
    }
}
