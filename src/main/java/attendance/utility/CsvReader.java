package attendance.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import attendance.exception.AttendanceFileException;

public class CsvReader {
    private static final String NOT_EXIST_FILE = "존재하지 않은 파일입니다.";
    private static final String INVALID_FILE = "유효하지 않은 파일입니다.";
    private static final int SKIP_CSV_LINE = 1;

    private final String fileName;

    public CsvReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getLines() throws AttendanceFileException {
        URL resourceUrl = getUrl(fileName);
        return readFile(resourceUrl);
    }

    private URL getUrl(String fileName) throws AttendanceFileException {
        Optional<URL> resourceUrl = Optional.ofNullable(getClass().getResource(fileName));
        return resourceUrl.orElseThrow(() -> new AttendanceFileException(NOT_EXIST_FILE));
    }

    public List<String> readFile(URL resourceUrl) throws AttendanceFileException {
        try (BufferedReader bufferedReader = new BufferedReader(
            new FileReader(resourceUrl.getFile()))) {
            return bufferedReader.lines()
                .skip(SKIP_CSV_LINE)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new AttendanceFileException(INVALID_FILE, e);
        }
    }
}
