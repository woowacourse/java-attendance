package model;

import static org.assertj.core.api.Assertions.assertThat;

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
}
