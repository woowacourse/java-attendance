package domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AttendanceFileLoaderTest {
    AttendanceFileLoader attendanceFileLoader;

    @BeforeEach
    void setUp() {
        FileReader fileReader = new FileReader();
        attendanceFileLoader = new AttendanceFileLoader(fileReader);
    }

    @DisplayName("파일을 읽어와서 초기 출석부 값으로 변경한다")
    @Test
    void fileConverterTest(@TempDir Path path) throws IOException {
        Path testPath = path.resolve("test.txt");

        List<String> lines = List.of(
                """
                        nickname,datetime
                        빙봉,2024-12-13 10:07
                        이든,2024-12-13 10:07
                        빙봉,2024-12-12 11:11
                        """);
        Files.write(testPath, lines);

        Map<String, Attendances> initialAttendanceBook = attendanceFileLoader.loadInitialAttendances(testPath);
        Assertions.assertThat(initialAttendanceBook).hasSize(2);
        Assertions.assertThat(initialAttendanceBook.get("빙봉").getRecords()).hasSize(2);
        Assertions.assertThat(initialAttendanceBook.get("이든").getRecords()).hasSize(1);
    }
}