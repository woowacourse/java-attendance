package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class CsvAttendanceDataReaderTest {
    @DisplayName("csvreader 값 대입 테스트")
    @Test
    void testLoadAttendanceData() {
        // given
        List<LocalDateTime> expected = List.of(
                LocalDateTime.of(2024, 12, 13, 10, 8),
                LocalDateTime.of(2024, 12, 11, 10, 2),
                LocalDateTime.of(2024, 12, 10, 10, 1),
                LocalDateTime.of(2024, 12, 9, 13, 3),
                LocalDateTime.of(2024, 12, 5, 10, 7),
                LocalDateTime.of(2024, 12, 4, 10, 2),
                LocalDateTime.of(2024, 12, 3, 10, 6),
                LocalDateTime.of(2024, 12, 2, 13, 1)
        );
        // when
        AttendanceDataReader attendanceDataReader = new CsvAttendanceDataReader();
        Map<String, List<LocalDateTime>> crews = attendanceDataReader.loadAttendanceData();
        List<LocalDateTime> result = crews.get("쿠키");
        // then
        assertThat(result.size()).isEqualTo(8);
        assertThat(result).isEqualTo(expected);
    }
}
