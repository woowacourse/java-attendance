package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {

    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static List<String> readFile() {
        List<String> contents = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            readLine(bufferedReader, contents);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일의 형식이 올바르지 않습니다.");
        }

        return contents;
    }

    private static void readLine(BufferedReader bufferedReader, List<String> contents) throws IOException {
        boolean isFirstLine = true;
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            isFirstLine = addIfNotFirstLine(contents, isFirstLine, line);
        }
    }

    private static boolean addIfNotFirstLine(List<String> contents, boolean isFirstLine, String line) {
        if (isFirstLine) {
            isFirstLine = false;
            return isFirstLine;
        }
        contents.add(line);
        return isFirstLine;
    }
}
