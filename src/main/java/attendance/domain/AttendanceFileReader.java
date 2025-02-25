package attendance.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import attendance.common.exception.AttendanceFileException;

public class AttendanceFileReader {
    private static final String NOT_EXIST_FILE = "존재하지 않은 파일입니다.";
    private static final String INVALID_FILE = "유효하지 않은 파일입니다.";
    private static final int SKIP_CSV_INFO = 1;

    private final String fileName;

    private AttendanceFileReader(String fileName) {
        this.fileName = fileName;
    }

    public static AttendanceFileReader from(String fileName) {
        return new AttendanceFileReader(fileName);
    }

    public List<String> getLines() throws AttendanceFileException {
        URL resourceUrl = getUrl(fileName);
        return readFile(resourceUrl);
    }

    private URL getUrl(String fileName) throws AttendanceFileException {
        URL resourceUrl = getClass().getResource(fileName);
        if (resourceUrl == null) {
            throw new AttendanceFileException(NOT_EXIST_FILE);
        }
        return resourceUrl;
    }

    public List<String> readFile(URL resourceUrl) throws AttendanceFileException {
        try (BufferedReader bufferedReader = new BufferedReader(
            new FileReader(resourceUrl.getFile()))) {
            return bufferedReader.lines()
                .skip(SKIP_CSV_INFO)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new AttendanceFileException(INVALID_FILE, e);
        }
    }
}
