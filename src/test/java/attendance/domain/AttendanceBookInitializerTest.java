package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;
import java.util.Map;

public class AttendanceBookInitializerTest {

    @Test
    void 읽어온_출석_기록들을_Map_타입으로_파싱한다() {
        // Given
        AttendanceBookInitializer attendanceBookInitializer = new AttendanceBookInitializer();
        List<String> crewAttendanceTexts = List.of("쿠키,2025-02-25 10:04", "빙봉,2025-02-26 10:07", "빙봉,2025-02-27 10:35");

        List<AttendanceDateTime> cookieAttendanceDateTimes = List.of(
                new AttendanceDateTime(Year.of(2025).atMonth(2).atDay(25).atTime(10, 4)));
        List<AttendanceDateTime> bingbongAttendanceDateTimes = List.of(
                new AttendanceDateTime(Year.of(2025).atMonth(2).atDay(26).atTime(10, 7)),
                new AttendanceDateTime(Year.of(2025).atMonth(2).atDay(27).atTime(10, 35)));
        Map<String, List<AttendanceDateTime>> expected = Map.of("쿠키", cookieAttendanceDateTimes, "빙봉", bingbongAttendanceDateTimes);

        // When
        Map<String, List<AttendanceDateTime>> crewAttendances = attendanceBookInitializer.parseTexts(crewAttendanceTexts);

        // Then
        Assertions.assertThat(crewAttendances)
                .isEqualTo(expected);
    }
}
