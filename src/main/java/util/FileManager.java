package util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String ROOT_PATH = "src/main/resources/";

    private FileManager() {
    }

    public static List<String> readFileLines(final String fileName) {
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get(ROOT_PATH + fileName)));
        } catch (Exception e) {
            throw new IllegalStateException("[ERROR] " + e.getMessage());
        }
    }
}
