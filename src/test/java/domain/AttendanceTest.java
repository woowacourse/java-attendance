package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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


    @DisplayName("캠퍼스 운영시간 전에는 출석을 받지 않는다.")
    @Test
    void test2() {
        LocalTime notValidTime = LocalTime.of(7, 59);

        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(validDate, notValidTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("캠퍼스 운영시간 후에는 출석을 받지 않는다.")
    @Test
    void test3() {
        LocalTime notValidTime = LocalTime.of(23, 1);

        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(validDate, notValidTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("주말에는 출석을 받지 않는다.")
    @Test
    void test4() {
        LocalDate notValidDate = LocalDate.of(2024, 12, 1);

        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(notValidDate, validTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("공휴일에는 출석을 받지 않는다.")
    @Test
    void test5() {
        LocalDate notValidDate = LocalDate.of(2024, 12, 25);

        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(notValidDate, validTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
