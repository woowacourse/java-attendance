package domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AttendancesLoaderTest {

    private static final Path path = Path.of("testAttendance.csv");
    private final AttendancesLoader loader = new AttendancesLoader();

    @BeforeAll
    public static void createCsvFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("testAttendance.csv"));
        writer.write("nickname,datetime\n");
        writer.write("빙봉,2024-12-13 10:07\n");
        writer.write("빙티,2024-12-13 10:07\n");
        writer.write("이든,2024-12-13 10:07\n");
        writer.write("빙봉,2024-12-12 11:11\n");
        writer.write("이든,2024-12-12 10:06\n");
        writer.write("짱수,2024-12-12 10:00\n");
        writer.write("빙봉,2024-12-11 10:02\n");

        writer.flush();
        writer.close();
    }

    @AfterAll
    public static void removeCsvFile() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void 크루의_출석부를_불러온다() throws IOException {
        Attendances attendances = loader.load(new FileReader("testAttendance.csv"));

        List<Attendance> logsWithCrew1 = attendances.getLogsWithName("빙봉");
        Attendance attendance = logsWithCrew1.getFirst();
        LocalDateTime localDateTime = attendance.getLocalDateTime();

        assertThat(logsWithCrew1.size()).isEqualTo(3);
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 7));
    }


}
