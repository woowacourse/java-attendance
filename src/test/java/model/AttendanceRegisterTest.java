package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRegister;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceRegisterTest {

    @Test
    void 크루가_오늘날짜로_출석부에_출석을_한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 10);
        attendanceRegister.attend("한스", attendanceDate, attendanceTime);

        // when
        AttendanceDateTime registryAttendanceDateTime = attendanceRegister.findAttendanceDateTimeByCrewName(
                "한스",
                LocalDate.of(2024, 12, 10)
        );

        // then
        assertThat(registryAttendanceDateTime).isEqualTo(new AttendanceDateTime(
                attendanceDate,
                attendanceTime
        ));
    }

    @Test
    void 크루가_특정_날짜의_출석을_조회했을때_출석이_존재하지_않으면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        attendanceRegister.attend("한스", attendanceDate, attendanceTime);

        // when & then
        Assertions.assertThatThrownBy(() -> attendanceRegister.findAttendanceDateTimeByCrewName(
                "한스",
                LocalDate.of(2024, 12, 9)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_크루가_출석을_조회하면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();

        // when & then
        Assertions.assertThatThrownBy(() -> attendanceRegister.findAttendanceDateTimeByCrewName(
                "한스",
                LocalDate.of(2024, 12, 9)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_날짜에_특정_크루의_출석_시간을_수정한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        attendanceRegister.attend("한스", attendanceDate, attendanceTime);
        LocalDate modifyDate = LocalDate.of(2024, 12, 10);
        LocalTime modifyTime = LocalTime.of(10, 0);
        attendanceRegister.modify("한스", modifyDate, modifyTime);

        // when
        AttendanceDateTime modifyAttendanceDateTime = attendanceRegister.findAttendanceDateTimeByCrewName(
                "한스",
                LocalDate.of(2024, 12, 10)
        );

        // then
        assertThat(modifyAttendanceDateTime).isEqualTo(new AttendanceDateTime(modifyDate, modifyTime));
    }

    @Test
    void 이미_출석한_날짜에_다시_출석을_시도할_경우_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        attendanceRegister.attend("한스", attendanceDate, attendanceTime);

        // when & then
        assertThatThrownBy(() -> {
            attendanceRegister.attend(
                    "한스",
                    LocalDate.of(2024, 12, 10),
                    LocalTime.of(10, 5)
            );
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 운영중이_아닌_요일에_출석을_하면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate date = LocalDate.of(2024, 12, 14);
        LocalTime time = LocalTime.of(10, 10);

        // when & then
        assertThatThrownBy(() -> attendanceRegister.attend("한스", date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @Test
    void 공휴일에_출석을_하면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(10, 10);

        // when & then
        assertThatThrownBy(() -> attendanceRegister.attend("한스", date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @Test
    void 캠퍼스_운영시간이_아닌_시간에_출석을_하면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(7, 58);

        // when & then
        assertThatThrownBy(() -> attendanceRegister.attend("한스", date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교시간이 아닙니다.");
    }

    @Test
    void 출석이_없는_날짜를_수정하면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        attendanceRegister.attend("한스", attendanceDate, attendanceTime);
        LocalDate modifyDate = LocalDate.of(2024, 12, 11);
        LocalTime modifyTime = LocalTime.of(10, 3);

        // when & then
        assertThatThrownBy(() -> attendanceRegister.modify("한스", modifyDate, modifyTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출석내역이 없는 날짜입니다.");
    }

    @Test
    void 캠퍼스_운영시간이_아닌_시간으로_수정하면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        attendanceRegister.attend("한스", attendanceDate, attendanceTime);
        LocalDate modifyDate = LocalDate.of(2024, 12, 10);
        LocalTime modifyTime = LocalTime.of(7, 3);

        // when & then
        assertThatThrownBy(() -> attendanceRegister.modify("한스", modifyDate, modifyTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("캠퍼스가 운영중이지 않은 시간입니다.");
    }
}
