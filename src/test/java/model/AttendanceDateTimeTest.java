package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceDateTimeTest {

    @Test
    @DisplayName("같은 경우")
    void 출결일시에서_출결일이_같은지_확인한다_1() {
        // given
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        AttendanceDate attendanceDate = new AttendanceDate(2024, 12, 10);

        // when
        boolean isEquals = attendanceDateTime.equalsDate(attendanceDate);

        // then
        assertThat(isEquals).isTrue();
    }

    @Test
    @DisplayName("다른 경우")
    void 출결일시에서_출결일이_같은지_확인한다_2() {
        // given
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        AttendanceDate attendanceDate = new AttendanceDate(2024, 12, 11);

        // when
        boolean isEquals = attendanceDateTime.equalsDate(attendanceDate);

        // then
        assertThat(isEquals).isFalse();
    }

    @Test
    void 출결일시에서_출결시간을_수정한다() {
        // given
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        LocalTime time = LocalTime.of(10, 7);

        // when
        attendanceDateTime.modifyAttendanceTime(time);

        // then
        assertThat(attendanceDateTime.getAttendanceTime()).isEqualTo(time);
    }

    @Test
    @DisplayName("운영시간 이전인 경우")
    void 캠퍼스_운영시간이_아닌_경우_예외가_발생한다_1() {
        // given

        // when & then
        assertThatThrownBy(() -> new AttendanceDateTime(
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(7, 59)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("운영시간 이후인 경우")
    void 캠퍼스_운영시간이_아닌_경우_예외가_발생한다_2() {
        // given

        // when & then
        assertThatThrownBy(() -> new AttendanceDateTime(
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(23, 1)
        )).isInstanceOf(IllegalArgumentException.class);
    }
}
