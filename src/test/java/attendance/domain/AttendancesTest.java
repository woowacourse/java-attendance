package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.file.AttendanceFileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendancesTest {

    private static String path = "src/test/resources/testAttendances.csv";
    private Attendances attendances;

    @BeforeEach
    void setUp() throws IOException {
        attendances = AttendanceFileReader.read(path).attendances();
    }

    @Test
    void 크루와_출석정보로_출석체크를_할수있다() {
        Crew crew = new Crew("크루");
        Attendance attendance = Attendance.of(LocalDateTime.of(2025, 2, 21, 10, 6));
        attendances.addAttendance(crew, attendance);

        Attendance saved = attendances.getAttendance(crew, LocalDate.of(2025, 2, 21));
        assertThat(saved).isEqualTo(attendance);
    }

    @Test
    void 크루와_날짜로_해당크루의_출석기록들을_얻을수있다() {
        Crew crew = new Crew("크루");
        Attendance attendance1 = Attendance.of(LocalDateTime.of(2025, 2, 19, 10, 0));
        Attendance attendance2 = Attendance.of(LocalDateTime.of(2025, 2, 20, 10, 0));
        attendances.addAttendance(crew, attendance1);
        attendances.addAttendance(crew, attendance2);

        List<Attendance> savedAttendances = attendances.getAttendances(crew, LocalDate.of(2025, 2, 21));
        assertThat(savedAttendances).contains(attendance1, attendance2);
    }

    @Test
    void 특정날짜_전날까지_크루의_결석을_포함하여_조회한다() {
        Crew crew = new Crew("크루");
        LocalDate _20250207Friday = LocalDate.of(2025, 2, 7);

        // 2025.02.03월 ~ 2025.02.06목 : 4일
        List<Attendance> savedAttendances = attendances.getAttendances(crew, _20250207Friday);
        assertThat(savedAttendances).hasSize(4);
    }

    @Test
    void _12월14일_기준으로_빙티의_출석기록은_10개이다() {
        Crew crew = new Crew("빙티");
        LocalDate localDate = LocalDate.of(2024, 12, 14);
        assertThat(attendances.getAttendances(crew, localDate)).hasSize(10);
    }

    @ParameterizedTest
    @CsvSource(value = {"빙티:INTERVIEW", "쿠키:REMOVAL"}, delimiterString = ":")
    void 지각횟수와_결석횟수로_제적위험자를_판단한다(String nickName, Penalty expected) {
        Crew crew = new Crew(nickName);
        LocalDate localDate = LocalDate.of(2024, 12, 14);
        int absenceCount = attendances.countAttendanceStatus(crew, localDate,
            AttendanceStatus.ABSENCE);
        int lateCount = attendances.countAttendanceStatus(crew, localDate, AttendanceStatus.LATE);
        Penalty penalty = Penalty.determine(absenceCount, lateCount);
        assertThat(penalty).isEqualTo(expected);
    }
}
