package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendancesTest {

    @Test
    @DisplayName("날짜와 시간으로 출석을 등록한다")
    void 날짜와_시간으로_출석을_등록한다() {
        // given
        Attendance defaultAttendance = new Attendance(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        Attendances attendances = new Attendances(List.of(defaultAttendance));

        LocalDateTime checkDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));

        // when
        Attendances newAttendances = attendances.registerAttendance(checkDateTime);
        Attendance result = newAttendances.findAttendanceByDate(checkDateTime.toLocalDate());

        // then
        assertAll(
                () -> assertThat(result.getDateTime()).isEqualTo(checkDateTime),
                () -> assertThat(result.getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }

    @Test
    @DisplayName("출석할때 이미 출석한 경우 예외가 발생한다")
    void 출석할때_이미_출석한_경우_예외가_발생한다() {
        // given
        Attendance defaultAttendance = new Attendance(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
        Attendances attendances = new Attendances(List.of(defaultAttendance));

        LocalDateTime attendanceDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendances.registerAttendance(attendanceDateTime))
                .withMessage("[ERROR] 이미 출석이 등록되었습니다. 수정 기능을 이용 해주세요.");
    }

    @Test
    @DisplayName("날짜와 시간으로 출석을 수정한다")
    void 날짜와_시간으로_출석을_수정한다() {
        // given
        Attendance defaultAttendance = new Attendance(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
        Attendances attendances = new Attendances(List.of(defaultAttendance));

        LocalDateTime updateDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));

        // when
        Attendances newAttendances = attendances.updateAttendance(updateDateTime);
        Attendance result = newAttendances.findAttendanceByDate(updateDateTime.toLocalDate());

        // then
        assertAll(
                () -> assertThat(result.getDateTime()).isEqualTo(updateDateTime),
                () -> assertThat(result.getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }
}
