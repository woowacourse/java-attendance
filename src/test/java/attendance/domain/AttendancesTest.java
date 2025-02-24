package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendancesTest {

    @Test
    void 찾으려는_날짜를_입력하면_출석_기록을_찾아준다() {
        // given
        List<LocalDateTime> attendanceDateTimes = List.of(LocalDateTime.of(2025, 2, 3, 10, 0));
        LocalDateTime today = LocalDateTime.of(2025, 2, 4, 10, 0);
        Attendances attendances = new Attendances(attendanceDateTimes, today);

        // when & then
        assertThat(attendances.findAttendanceByLocalDate(LocalDate.of(2025, 2, 3))).isNotNull();
    }

    @Test
    void 오늘_날짜를_알려주면_전날까지의_해당_크루의_출석_기록을_알려준다() {
        // Given
        List<LocalDateTime> attendanceDateTimes = List.of(LocalDateTime.of(2025, 2, 3, 10, 0));
        LocalDateTime today = LocalDateTime.of(2025, 2, 6, 10, 0);
        Attendances attendances = new Attendances(attendanceDateTimes, today);

        // When & Then
        assertThat(attendances.findAllBeforeToday(today)).hasSize(3);
    }

    @CsvSource(value = {
            "4,false", "3,true"
    })
    @ParameterizedTest
    void 날짜를_알려주면_해당_날짜의_출석기록이_존재하는지_알려준다(int day, boolean expected) {
        List<LocalDateTime> attendanceDateTimes = List.of(LocalDateTime.of(2025, 2, 3, 10, 0));
        LocalDateTime today = LocalDateTime.of(2025, 2, 4, 10, 0);
        Attendances attendances = new Attendances(attendanceDateTimes, today);

        assertThat(attendances.existsByLocalDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

    @Test
    void 현재_출석_상태_별_횟수를_알려준다() {
        Attendances attendances = new Attendances(List.of(
                LocalDateTime.of(2025, 2, 3, 10, 0),
                LocalDateTime.of(2025, 2, 4, 10, 6),
                LocalDateTime.of(2025, 2, 5, 10, 31)
        ), LocalDateTime.of(2025, 2, 6, 10, 0));

        assertThat(attendances.calculateStatusCount()).containsKeys("출석", "지각", "결석")
                .containsValues(1, 1, 1);
    }

    @Test
    void 현재_출석_상태를_통해_제적_위험_대상인지_알려준다() {
        Attendances attendances = new Attendances(List.of(
                LocalDateTime.of(2025, 2, 4, 10, 31),
                LocalDateTime.of(2025, 2, 5, 10, 31),
                LocalDateTime.of(2025, 2, 6, 10, 31),
                LocalDateTime.of(2025, 2, 7, 10, 31),
                LocalDateTime.of(2025, 2, 11, 10, 31),
                LocalDateTime.of(2025, 2, 12, 10, 30),
                LocalDateTime.of(2025, 2, 13, 10, 30),
                LocalDateTime.of(2025, 2, 14, 10, 30)
        ), LocalDateTime.of(2025, 2, 15, 10, 0));

        assertThat(attendances.calculateExpulsionStatus()).isEqualTo(ExpulsionStatus.EXPULSION);
    }

}
