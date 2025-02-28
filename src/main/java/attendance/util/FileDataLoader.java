package attendance.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileDataLoader {

    private FileDataLoader() {
    }

    public static Optional<List<String>> loadLines(final String filePath) {
        try {
            final List<String> readAllLines = Files.readAllLines(
                Path.of(filePath));

            return Optional.of(readAllLines);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
