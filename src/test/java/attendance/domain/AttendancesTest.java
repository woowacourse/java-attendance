package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendancesTest {

    @DisplayName("출석을 추가할 수 있다.")
    @Test
    void 출석을_추가한다() {
        // given
        Attendances attendances = new Attendances();
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 5, 10, 0);

        // when
        Attendance result = attendances.addAttendance(dateTime);

        // than
        assertAll(
                () -> assertThat(result.getDateTime()).isEqualTo(dateTime),
                () -> assertThat(result.getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }

    @Test
    void 출석을_삭제한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 5, 10, 0);

        Attendances attendances = new Attendances();
        attendances.addAttendance(dateTime);

        // when
        Attendance attendance = attendances.deleteAttendance(dateTime.toLocalDate());

        // then
        assertThat(attendance.getDateTime())
                .isEqualTo(dateTime);
    }

    @DisplayName("해당 날짜의 출석을 찾을 수 있다.")
    @Test
    void 해당_날짜의_출석을_찾을_수_있다() {
        // given
        Attendances attendances = new Attendances();

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        attendances.addAttendance(dateTime);

        // when
        Attendance result = attendances.find(dateTime.toLocalDate());

        // then
        assertThat(result.getDateTime()).isEqualTo(dateTime);
    }

    @Test
    void 이미_출석한_크루의_출석_확인할_경우_에러가_발생한다() {
        // given
        Attendances attendances = new Attendances();

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 5, 10, 0, 0);
        attendances.addAttendance(dateTime);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendances.validateAlreadyAttendance(dateTime.toLocalDate()))
                .withMessage("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
    }

    @Test
    void 특정_일자_이전_출석을_반환한다() {
        // given
        Attendances attendances = new Attendances();

        LocalDate date = LocalDate.of(2024, 12, 3);
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 1, 10, 0),
                LocalDateTime.of(2024, 12, 2, 10, 0),
                LocalDateTime.of(2024, 12, 3, 10, 0)
        );

        dateTimes.forEach(attendances::addAttendance);

        // when
        List<Attendance> result = attendances.getAttendancesBefore(date);

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}
