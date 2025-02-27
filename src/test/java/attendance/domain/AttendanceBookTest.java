package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;
import java.util.Map;

public class AttendanceBookTest {

    @Test
    void 크루들의_이전_출석_기록들을_바탕으로_출석부를_생성한다() {
        // Given
        List<AttendanceDateTime> cookieAttendanceDateTimes = List.of(
                new AttendanceDateTime(Year.of(2025).atMonth(2).atDay(25).atTime(10, 4)));
        List<AttendanceDateTime> bingbongAttendanceDateTimes = List.of(
                new AttendanceDateTime(Year.of(2025).atMonth(2).atDay(26).atTime(10, 7)),
                new AttendanceDateTime(Year.of(2025).atMonth(2).atDay(27).atTime(10, 35)));
        Map<String, List<AttendanceDateTime>> crewAttendances = Map.of("쿠키", cookieAttendanceDateTimes, "빙봉", bingbongAttendanceDateTimes);

        // When & Then
        Assertions.assertThatCode(() -> new AttendanceBook(crewAttendances))
                .doesNotThrowAnyException();
    }
}
