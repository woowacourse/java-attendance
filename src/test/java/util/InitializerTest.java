package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import model.AttendanceHistory;
import model.AttendanceHistoryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InitializerTest {

    @DisplayName("출석 저장 정보 문자열 리스트로 출석 저장 정보를 불러온다")
    @Test
    void loadAttendanceBookTest() {
        String rawLines = "nickname,datetime\n"
                + "빙봉,2024-12-13 10:07\n"
                + "빙티,2024-12-13 10:07\n"
                + "이든,2024-12-13 10:07\n"
                + "빙봉,2024-12-12 11:11\n"
                + "이든,2024-12-12 10:06";
        List<String> lines = Arrays.stream(rawLines.split("\\n")).toList();

        Map<String, AttendanceHistory> expected = Map.of(
                "빙봉", AttendanceHistoryFactory.make(Stream.of(
                        LocalDateTime.of(2024, 12, 13, 10, 7),
                        LocalDateTime.of(2024, 12, 12, 11, 11))),
                "빙티", AttendanceHistoryFactory.make(Stream.of(
                        LocalDateTime.of(2024, 12, 13, 10, 7))),
                "이든", AttendanceHistoryFactory.make(Stream.of(
                        LocalDateTime.of(2024, 12, 13, 10, 7),
                        LocalDateTime.of(2024, 12, 12, 10, 6)
                ))
        );

        assertThat(Initializer.loadAttendanceBook(lines)).containsExactlyInAnyOrderEntriesOf(expected);

    }
}
