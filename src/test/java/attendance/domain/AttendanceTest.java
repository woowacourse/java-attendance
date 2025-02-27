package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    Attendance attendance1;
    Attendance attendance2;

    @BeforeEach
    void setUp() {
        attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
        attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
    }

    @DisplayName("출석 시간을 수정한다.")
    @Test
    void 출석_시간을_수정한다() {

        // given
        LocalTime modifyTime = LocalTime.of(10, 5);

        // when
        Attendance modifyAttendance = attendance1.modifyAttendanceTime(modifyTime);

        // then
        assertThat(attendance1).isEqualTo(modifyAttendance);

    }
}
