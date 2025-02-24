package attendance.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<List<String>> readCSV(Path path) {
        List<List<String>> csvData = new ArrayList<>();
        try {
            List<String> records = Files.readAllLines(path);
            for (int i = 1; i < records.size(); i++) {
                csvData.add(List.of(records.get(i).split(",")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return csvData;
    }


}
