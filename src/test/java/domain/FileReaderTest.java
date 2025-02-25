package domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderTest {
    FileReader fileReader = new FileReader();

    @DisplayName("파일을 첫째 줄을 빼고 줄 별로 읽어온다")
    @Test
    void fileReadTest(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("file.txt");

        List<String> lines = List.of("""
                nickname,datetime
                쿠키,2024-12-13 10:08
                빙봉,2024-12-13 10:07
                """);
        Files.write(testFile, lines);

        for(String line : fileReader.readLines(testFile)) {
            System.out.println("line = " + line);
        }
        Assertions.assertEquals(2, fileReader.readLines(testFile).size());
    }

    @DisplayName("파일이 존재하지 않으면 빈 리스트를 반환한다")
    @Test
    void FileNotFoundExceptionTest() {
        Path testFile = Path.of("noFile.txt");

        Assertions.assertEquals(0, fileReader.readLines(testFile).size());
    }
}
