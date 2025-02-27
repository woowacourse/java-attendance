package domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AttendancesFileTest {
    LocalDate today = LocalDate.of(2024, 12, 13);
    AttendancesFile attendancesFile = new AttendancesFile();

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

        Map<String, Attendances> initialAttendanceBook = attendancesFile.loadInitialAttendances(testPath, today);
        Assertions.assertThat(initialAttendanceBook).hasSize(2);
        Assertions.assertThat(initialAttendanceBook.get("빙봉").getRecords()).hasSize(10);
        Assertions.assertThat(initialAttendanceBook.get("이든").getRecords()).hasSize(10);
    }

    @DisplayName("파일이 존재하지 않으면 빈 맵를 반환한다")
    @Test
    void FileNotFoundExceptionTest() {
        Path testPath = Path.of("noFile.txt");

        Assertions.assertThat(attendancesFile.loadInitialAttendances(testPath, today)).isEmpty();
    }
}