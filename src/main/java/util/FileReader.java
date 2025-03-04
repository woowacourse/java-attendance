package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileReader {
    public static List<String> readFile() {
        Path path = Paths.get("src", "main", "resources", "attendances.csv");
        return convertFile(path);
    }

    protected static List<String> convertFile(Path path) {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.skip(1).toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
