package attendance.domain;

import org.assertj.core.api.Assertions;
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
        Attendance defaultAttendance = Attendance.fromDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
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
        Attendance defaultAttendance = Attendance.fromDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
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
        Attendance defaultAttendance = Attendance.fromDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
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

    @Test
    @DisplayName("특정 이전 날짜의 출석 날짜, 시간과 출결 상황을 반환한다")
    void 특정_이전_날짜의_출석_날짜_시간과_출결_상황을_반환한다() {
        // given
        LocalDate nowDate = LocalDate.now();

        Attendances attendances = new Attendances(List.of(
                Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.of(13, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        ));

        List<Attendance> exceptedRecord = List.of(
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        );

        // when
        List<Attendance> result = attendances.getAttendancesBefore(LocalDate.now());

        // then
        Assertions.assertThat(result)
                .containsExactlyElementsOf(exceptedRecord);
    }
}
