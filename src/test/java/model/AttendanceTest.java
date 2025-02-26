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
        LocalDateTime registryAttendanceDateTime = attendanceRegister.findAttendanceByCrewName("한스",
                LocalDate.of(2024, 12, 10));

        // then
        Assertions.assertThat(registryAttendanceDateTime).isEqualTo(attendanceDateTime);
    }

    @Test
    void 크루가_특정_날짜의_출석을_조회했을때_출석이_존재하지_않으면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 10, 10, 5);
        attendanceRegister.attend("한스", attendanceDateTime);

        // when & then
        Assertions.assertThatThrownBy(() -> attendanceRegister.findAttendanceByCrewName(
                "한스",
                LocalDate.of(2024, 12, 9)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_크루가_출석을_조회하면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();

        // when & then
        Assertions.assertThatThrownBy(() -> attendanceRegister.findAttendanceByCrewName(
                "한스",
                LocalDate.of(2024, 12, 9)
        )).isInstanceOf(IllegalArgumentException.class);
    }


}
