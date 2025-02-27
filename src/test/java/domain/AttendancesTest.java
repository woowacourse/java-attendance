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

    @DisplayName("이미 출석을 한 경우 예외를 발생시킨다")
    @Test
    void addAttendanceException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> attendances.addAttendance(LocalDateTime.of(2024, 12, 2, 10, 0)));
        Assertions.assertEquals(1, attendances.getRecords().size());
    }

    @DisplayName("출석을 수정한다")
    @Test
    void updateAttendance() {
        attendances.updateAttendance(LocalDateTime.of(2024, 12, 2, 15, 0), 3);
        Assertions.assertEquals(1, attendances.getRecords().size());
    }

    @DisplayName("미래 시점인 경우 수정하지 않고 예외를 발생시킨다.")
    @Test
    void updateAttendanceFutureExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> attendances.updateAttendance(LocalDateTime.of(2024, 12, 2, 15, 0), 1));
    }

    @DisplayName("크루의 출석 상태를 계산한다")
    @Test
    void calculateCrewStatusTest() {
        attendances.addAttendance(LocalDateTime.of(2024, 12, 3, 10, 59));
        attendances.addAttendance(LocalDateTime.of(2024, 12, 4, 11, 0));
        attendances.addAttendance(LocalDateTime.of(2024, 12, 5, 13, 0));
        Assertions.assertEquals(CrewStatus.COUNSEL, attendances.calculateCrewStatus(5));
    }
}
