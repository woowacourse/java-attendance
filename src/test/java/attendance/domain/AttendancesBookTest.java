package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.file.AttendanceFileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendancesBookTest {
    private static String path = "src/test/resources/testAttendances.csv";
    private AttendancesBook attendancesBook;

    @BeforeEach
    void setUp() throws IOException {
        attendancesBook = AttendanceFileReader.read(path).attendancesBook();
    }

    @Test
    void _12월14일_기준으로_빙티의_출석기록은_10개이다() {
        Crew crew = new Crew("빙티");
        LocalDate localDate = LocalDate.of(2024, 12, 14);
        assertThat(attendancesBook.getAttendancesOfCrew(crew, localDate)).hasSize(10);
    }

    @ParameterizedTest
    @CsvSource(value = {"빙티:INTERVIEW", "쿠키:REMOVAL"}, delimiterString = ":")
    void 지각횟수와_결석횟수로_제적위험자를_판단한다(String nickName, Penalty expected) {
        Crew crew = new Crew(nickName);
        LocalDate localDate = LocalDate.of(2024, 12, 14);
        List<Attendance> attendances = attendancesBook.getAttendancesOfCrew(crew, localDate);
        int absenceCount = attendancesBook.countAttendanceStatus(attendances, AttendanceStatus.ABSENCE);
        int lateCount = attendancesBook.countAttendanceStatus(attendances, AttendanceStatus.LATE);
        Penalty penalty = Penalty.determine(absenceCount, lateCount);
        assertThat(penalty).isEqualTo(expected);
    }
}
