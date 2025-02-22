package attendance.util;

import attendance.exception.ErrorMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileReader {

    public static Set<String> fileReadCrewNames(String fileName) throws IOException {
        List<String> fileLines = fileReadLine(fileName);
        Set<String> crewNames = new HashSet<>();
        for (String fileLine : fileLines) {
            String crewName = List.of(fileLine.split(",")).get(0);
            crewNames.add(crewName);
        }
        return crewNames;
    }

    public static List<String> fileReadLine(String fileName) throws IOException {
        try (BufferedReader br = loadFile(fileName)) {
            List<String> items = new ArrayList<>();
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
            return items;
        }
    }

    private static BufferedReader loadFile(String fileName) throws IOException {
        try (InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException(ErrorMessage.FILE_NOT_PRESENCE.getMessage());
            }
            return new BufferedReader(new InputStreamReader(inputStream));
        }
    }
}
