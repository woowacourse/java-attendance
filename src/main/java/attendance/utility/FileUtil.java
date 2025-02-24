package attendance.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public final class FileUtil {

    private static final String DIRECTORY_PATH = "src/main/resources/";
    private static final String NOT_FOUND_FILE = "[ERROR] 파일 이름을 찾을 수 없습니다. (파일이름: %s)";
    private static final int HEADER_LINE_INDEX = 1;

    private FileUtil() {
    }

    public static List<String> readFile(final String fileName) {
        BufferedReader fileReader = openFileReader(fileName);
        return parseLines(fileReader);
    }

    private static BufferedReader openFileReader(final String fileName) {
        try {
            File file = new File(DIRECTORY_PATH + fileName);
            FileReader fileReader = new FileReader(file);
            return new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format(NOT_FOUND_FILE, fileName));
        }
    }

    private static List<String> parseLines(final BufferedReader fileReader) {
        List<String> lines = fileReader.lines().toList();
        return lines.subList(HEADER_LINE_INDEX, lines.size());
    }
}
