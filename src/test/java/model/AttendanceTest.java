package model;

import attendance.model.AttendanceRegister;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 크루가_오늘날짜로_출석부에_출석을_한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 10, 10, 5);
        attendanceRegister.attend("한스", attendanceDateTime);

        // when
        LocalDateTime registryAttendanceDateTime = attendanceRegister.findAttendanceByName("한스",
                LocalDate.of(2024, 12, 10));

        // then
        Assertions.assertThat(registryAttendanceDateTime).isEqualTo(attendanceDateTime);
    }
}
