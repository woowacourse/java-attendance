package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.ERROR_HEADER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    LocalDate validDate = LocalDate.of(2024, 12, 12);
    LocalTime validTime = LocalTime.of(10, 0);

    @DisplayName("출석을 정상적으로 저장한다.")
    @Test
    void test1() {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        assertDoesNotThrow(() -> new Attendance(LocalDateTime.of(validDate, startTime)));
        assertDoesNotThrow(() -> new Attendance(LocalDateTime.of(validDate, endTime)));
    }
}
