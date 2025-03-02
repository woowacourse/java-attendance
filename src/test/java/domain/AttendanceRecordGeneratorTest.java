package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRecordGeneratorTest {


    @Test
    @DisplayName("2024-12-01 기준으로 등교일 출석 기록을 초기화 한다")
    void test1() {
        //given
        //when
        final Map<LocalDate, AttendanceRecord> attendanceMap = AttendanceRecordGenerator.generate();

        //then
        assertThat(attendanceMap).containsKey(LocalDate.of(2024, 12, 2))
                .containsKey(LocalDate.of(2024, 12, 31))
                .doesNotContainKey(LocalDate.of(2024, 12, 1))
                .doesNotContainKey(LocalDate.of(2024, 12, 25))
                .doesNotContainKey(LocalDate.of(2024, 12, 28));
    }
}
