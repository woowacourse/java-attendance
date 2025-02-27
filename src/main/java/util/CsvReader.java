package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    private static final Path STORE_PATH = Path.of("src/main/resources/attendances.csv");
    private static Map<String, List<LocalDateTime>> attendances;

    public static Map<String, List<LocalDateTime>> generateAttendances() throws IOException {
        attendances = new HashMap<>();
        List<String> lines = Files.readAllLines(STORE_PATH);
        for (String line : lines.subList(1, lines.size())) {
            addInfoFromLine(line);
        }
        return attendances;
    }

    private static void addInfoFromLine(String line) {
        String[] fields = line.split(",");
        String name = fields[0];
        attendances.computeIfAbsent(name, k -> new ArrayList<>());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(fields[1], formatter);
        attendances.get(name).add(dateTime);
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
