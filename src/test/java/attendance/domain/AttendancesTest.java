package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttendancesTest {

    @Test
    @DisplayName("날짜와 시간으로 출석을 등록한다")
    void 날짜와_시간으로_출석을_등록한다() {
        // given
        Attendance defaultAttendance = new Attendance(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        Attendances attendances = new Attendances(List.of(defaultAttendance));

        LocalDateTime attendanceDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));

        // when
        Attendances newAttendances = attendances.registerAttendance(attendanceDateTime);
        Attendance result = newAttendances.findAttendanceByDate(attendanceDateTime.toLocalDate());

        // then
        assertThat(result.getDateTime())
                .isEqualTo(attendanceDateTime);
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
}
