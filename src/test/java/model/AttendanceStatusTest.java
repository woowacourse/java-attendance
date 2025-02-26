package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    @DisplayName("월요일 출석인 경우")
    void 입력한_일시의_출결_상태를_확인한다_1() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime time = LocalTime.of(13, 0);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("월요일 지각인 경우")
    void 입력한_일시의_출결_상태를_확인한다_2() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime time = LocalTime.of(13, 6);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("월요일 결석인 경우")
    void 입력한_일시의_출결_상태를_확인한다_3() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime time = LocalTime.of(13, 31);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("월요일이 아닌 평일이 출석인 경우")
    void 입력한_일시의_출결_상태를_확인한다_4() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(10, 0);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("월요일이 아닌 평일이 지각인 경우")
    void 입력한_일시의_출결_상태를_확인한다_5() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(10, 6);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("월요일이 아닌 평일이 결석인 경우")
    void 입력한_일시의_출결_상태를_확인한다_6() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(10, 31);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
