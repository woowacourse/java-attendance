package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceConvertorTest {

    @DisplayName("출석 기록 변환 테스트")
    @Test
    void convertToAttendancesTest() {
        String firstAttendance = "메이,2024-12-12 10:03";
        String secondAttendance = "메이,2024-12-13 10:08";

        Map<String, List<LocalDateTime>> attendances = AttendanceConvertor.convertToAttendances(
                List.of(firstAttendance, secondAttendance));

        assertThat(attendances.get("메이"))
                .containsAll(List.of(
                        LocalDateTime.of(2024, 12, 12, 10, 3),
                        LocalDateTime.of(2024, 12, 13, 10, 8)));
    }
}
