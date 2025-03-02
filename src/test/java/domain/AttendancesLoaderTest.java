package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @AfterEach
    public void removeCsvFile() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void 크루의_출석부를_불러온다() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("testAttendance.csv"));
        String csvFileFormat = """
                nickname,datetime
                빙봉,2024-12-13 10:07
                빙티,2024-12-13 10:07
                이든,2024-12-13 10:07
                빙봉,2024-12-12 11:11
                이든,2024-12-12 10:06
                짱수,2024-12-12 10:00
                빙봉,2024-12-11 10:02
                """;
        writer.write(csvFileFormat);
        writer.flush();
        writer.close();

        AttendancesLoader loader = new AttendancesLoader();
        Attendances attendances = loader.load(new FileReader("testAttendance.csv"));
        List<Attendance> logsWithCrew1 = attendances.getLogsWithName(new Nickname("빙봉"));
        Attendance attendance = logsWithCrew1.getFirst();
        LocalDateTime localDateTime = attendance.getLocalDateTime();

        assertThat(logsWithCrew1.size()).isEqualTo(3);
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 7));
    }

    @ParameterizedTest
    @ValueSource(strings = {"""
            nickname,datetime
            빙봉,2024-12-13^10:07
            빙티,2024-12-13 10:07
            """, """
            nickname,datetime
            빙봉,2024-12-13-10:07
            빙티,2024-12-13 10:07
            """, """
            nickname,datetime
            빙봉:2024-12-13 10:07
            빙티-2024-12-13 10:07
            """})
    void 잘못된_포맷을_읽을_경우_예외를_발생시킨다(String csvFileFormat) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("testAttendance.csv"));
        writer.write(csvFileFormat);
        writer.flush();
        writer.close();

        AttendancesLoader loader = new AttendancesLoader();
        Assertions.assertThatThrownBy(() -> loader.load(new FileReader("testAttendance.csv"))).isInstanceOf(IOException.class).hasMessage("[ERROR] 출석 파일을 읽는 중 오류가 발생했습니다.");
    }


}
