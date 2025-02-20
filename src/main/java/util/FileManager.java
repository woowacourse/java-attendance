package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileManager {

    private FileManager() {
    }

    public static List<String> readFileLines(final String fileName) {
        final String path = "src/main/resources/";
        try {
            return Files.readAllLines(Path.of(path + fileName));
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
