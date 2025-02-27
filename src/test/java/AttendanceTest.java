import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceTest {
    @DisplayName("출석 기록이 존재하지 않는 경우 새로운 출석을 등록할 수 있다.")
    @Test
    void test1() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(10, 0);

        // when & then
        assertDoesNotThrow(() -> attendanceStorage.register(date, enterTime));
    }

    @DisplayName("출석 기록이 존재하는 경우 출석을 등록할 수 없다.")
    @Test
    void test3() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(10, 0);
        attendanceStorage.register(date, enterTime);

        // when &  then
        assertThatThrownBy(() -> {
            attendanceStorage.register(date, LocalTime.of(10, 1));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
