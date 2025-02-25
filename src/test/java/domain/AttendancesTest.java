package domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancesTest {
    Attendances attendances;

    @BeforeEach
    void setUp() {
        attendances = new Attendances();
        attendances.addAttendance(LocalDateTime.of(2024, 12, 2, 13, 0));
    }

    @DisplayName("출석을 추가한다")
    @Test
    void addAttendance() {
        Assertions.assertDoesNotThrow(() -> attendances.addAttendance(LocalDateTime.of(2024, 12, 3, 10, 0)));
        Assertions.assertEquals(2, attendances.getRecords().size());
    }

    @DisplayName("동일한 날짜가 있다면 출석을 삭제하고 추가한다")
    @Test
    void removeAttendance() {
        attendances.addAttendance(LocalDateTime.of(2024, 12, 2, 15, 0));
        Assertions.assertEquals(1, attendances.getRecords().size());
    }
}
