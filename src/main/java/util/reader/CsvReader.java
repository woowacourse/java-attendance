package util.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private CsvReader() {
    }

    public static List<String[]> readCsvLines(String csvFilePath) throws IOException {
        Path filePath = Path.of(csvFilePath);
        List<String> lines = readLinesFromFile(filePath);
        return splitLinesByComma(lines);
    }

    private static List<String> readLinesFromFile(Path filePath) throws IOException {
        return Files.readAllLines(filePath);
    }

    private static List<String[]> splitLinesByComma(List<String> lines) {
        List<String[]> splitLines = new ArrayList<>();
        for (String line : lines) {
            splitLines.add(line.split(","));
        }
        return splitLines;
    }
}
