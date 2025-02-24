import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FileReaderPolicy {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    FileReader fileReader;

    public FileReaderPolicy() {
        this.fileReader = readFile();
    }

    private FileReader readFile() {
        try {
            return new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("[ERROR] 파일 위치가 올바르지 않습니다");
        }
    }

    public void validateFileFormat() {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = bufferedReader.lines()
                .skip(1)
                .toList();

        for (String line : lines) {
            String[] splitLine = line.split(",");
            if (splitLine.length != 2) {
                throw new IllegalArgumentException("[ERROR] 파일 형식이 잘못되었습니다");
            }

            LocalDateTime dateTime = parseAttendanceDateTime(splitLine);
        }
    }

    private static LocalDateTime parseAttendanceDateTime(String[] splitLine) {
        try{
           return LocalDateTime.parse(splitLine[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 잘못되었습니다");
        }
    }
}
