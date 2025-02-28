package attendance.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private FileReader() {
    }

    public static List<String> parseToFile(final String filePath) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {

            List<String> readingFileResult = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                readingFileResult.add(line);
            }

            return readingFileResult;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
