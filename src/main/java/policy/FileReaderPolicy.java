package policy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FileReaderPolicy {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    private static final String SPLIT_DELIMITER = ",";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int LINE_SPLIT_COUNT = 2;
    private static final int HEADER = 1;

    FileReader fileReader;

    public FileReaderPolicy(String filePath) {
        this.fileReader = readFile(filePath);
    }

    public void createAttendances() {
        List<String> lines = readLines();

        for (String line : lines) {
            String[] splitLine = line.split(SPLIT_DELIMITER);
            validateSplitLineFormat(splitLine);

            String nickname = splitLine[0];
            LocalDateTime dateTime = parseAttendanceDateTime(splitLine[1]);
        }
    }

    private List<String> readLines() {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return bufferedReader.lines()
                .skip(HEADER)
                .toList();
    }

    private FileReader readFile(String filePath) {
        try {
            return new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("[ERROR] 파일 위치가 올바르지 않습니다");
        }
    }

    public void validateSplitLineFormat(String[] splitLine) {
        if (splitLine.length != LINE_SPLIT_COUNT) {
            throw new IllegalArgumentException("[ERROR] 파일 형식이 잘못되었습니다");
        }
    }

    public LocalDateTime parseAttendanceDateTime(String dateTime) {
        try{
           return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 잘못되었습니다");
        }
    }
}
