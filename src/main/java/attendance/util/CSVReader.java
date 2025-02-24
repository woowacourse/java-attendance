package attendance.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static final String COMMA = ",";

    public static List<List<String>> readCSV(Path path) {
        List<List<String>> csvData = new ArrayList<>();
        try {
            addData(path, csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return csvData;
    }

    private static void addData(Path path, List<List<String>> csvData) throws IOException {
        List<String> records = Files.readAllLines(path);
        for (int i = 1; i < records.size(); i++) {
            csvData.add(List.of(records.get(i).split(COMMA)));
        }
    }


}
