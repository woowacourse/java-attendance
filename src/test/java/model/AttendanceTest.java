package model;

import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRegister;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 크루가_오늘날짜로_출석부에_출석을_한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 10)
        );
        attendanceRegister.attend("한스", attendanceDateTime);

        // when
        AttendanceDateTime registryAttendanceDateTime = attendanceRegister.findAttendanceByCrewName(
                "한스",
                LocalDate.of(2024, 12, 10)
        );

        // then
        Assertions.assertThat(registryAttendanceDateTime).isEqualTo(attendanceDateTime);
    }

    @Test
    void 크루가_특정_날짜의_출석을_조회했을때_출석이_존재하지_않으면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
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

    @Test
    void 특정_날짜에_특정_크루의_출석_시간을_수정한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        attendanceRegister.attend("한스", attendanceDateTime);
        LocalDate modifyDate = LocalDate.of(2024, 12, 10);
        LocalTime modifyTime = LocalTime.of(10, 0);
        attendanceRegister.modify("한스", modifyDate, modifyTime);

        // when
        AttendanceDateTime modifyAttendanceDateTime = attendanceRegister.findAttendanceByCrewName(
                "한스",
                LocalDate.of(2024, 12, 10)
        );

        // then
        Assertions.assertThat(modifyAttendanceDateTime).isEqualTo(new AttendanceDateTime(modifyDate, modifyTime));
    }


}
