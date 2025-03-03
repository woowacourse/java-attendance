package util.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileParser {

    private FileParser() {
    }

    public static Map<String, List<LocalDateTime>> parseScannerToMap(Scanner scanner) {
        Map<String, List<LocalDateTime>> result = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] attributes = scanner.nextLine().split(",");
            String name = attributes[0];
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime(attributes[1]);

            result.putIfAbsent(name, new ArrayList<>());
            result.get(name).add(dateTime);
        }
        return result;
    }
}