package attendance.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReader {

    private static final String SPLITTER = ",";
    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final int START_LINE_INDEX = 1;

    public static Map<String, List<LocalDateTime>> read() {
        List<String> inputs = readAllLines();
        Map<String, List<LocalDateTime>> histories = new HashMap<>();
        for (String input : inputs) {
            add(input, histories);
        }
        return histories;
    }

    private static void add(final String input, final Map<String, List<LocalDateTime>> histories) {
        String[] tokens = input.split(SPLITTER);
        String nickname = tokens[0];
        LocalDateTime attendanceDateTime = StringParser.parseLocalDateTime(tokens[1]);
        List<LocalDateTime> history = histories.getOrDefault(nickname, new ArrayList<>());
        history.add(attendanceDateTime);
        histories.put(nickname, history);
    }

    private static List<String> readAllLines() {
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            return lines.subList(START_LINE_INDEX, lines.size());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
