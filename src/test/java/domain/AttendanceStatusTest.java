package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    void 출석_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new DateTime(new Date(LocalDate.of(2024, 12, 13)), new Time(10, 0))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 지각_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new DateTime(new Date(LocalDate.of(2024, 12, 13)), new Time(10, 10))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PERCEPTION);
    }

    @Test
    void 결석_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new DateTime(new Date(LocalDate.of(2024, 12, 13)), new Time(10, 35))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 주말에_출석할_수_없다() {
        // given & when & then
        assertThatThrownBy(() -> AttendanceStatus.from(
                        new DateTime(new Date(LocalDate.of(2024, 12, 14)), new Time(10, 0))
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }

    @Test
    void 캠퍼스_운영_시간이_아니다() {
        // given & when & then
        assertThatThrownBy(
                () -> AttendanceStatus.from(
                        new DateTime(new Date(LocalDate.of(2024, 12, 14)), new Time(7, 30))
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영 시간이 아닙니다.");
    }
}
