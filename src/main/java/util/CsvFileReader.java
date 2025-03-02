package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileReader implements FileReader {

    private final String filePath;

    public CsvFileReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String> readFile() {
        Path path = Path.of(filePath);

        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
