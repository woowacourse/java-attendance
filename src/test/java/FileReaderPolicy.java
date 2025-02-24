import java.io.FileNotFoundException;
import java.io.FileReader;

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
    }
}
