package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class CsvReader {

    private static final String DELIMITER = ",";
    private static final int LINES_TO_SKIP = 1;


    private CsvReader() {
    }

    public static List<String[]> readFile(final String filePath) {
        try (final BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .skip(LINES_TO_SKIP)
                    .map(line -> line.split(DELIMITER))
                    .toList();
        } catch (final IOException e) {
            throw new IllegalStateException();
        }
    }
}
