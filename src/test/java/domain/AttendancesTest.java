package domain;

import static org.assertj.core.api.Assertions.assertThat;

import file.AttendanceFileReader;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendancesTest {
    private static String path = "src/test/resources/testAttendances.csv";
    private Attendances attendances;

    @BeforeEach
    void setUp() throws IOException {
        attendances = AttendanceFileReader.read(path);
    }

    @Test
    void _12월14일_기준으로_빙티의_출석기록은_10개이다() {
        Crew crew = new Crew("빙티");
        LocalDate localDate = LocalDate.of(2024, 12, 14);
        assertThat(attendances.getByCrew(crew, localDate)).hasSize(10);
    }
}
